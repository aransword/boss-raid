package dev.raid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameRenderer {
    private static final int MAX_LOGS = 8;
    private static final int BAR_WIDTH = 20;
    private static final int BOX_INNER_WIDTH = 54; // ║ 과 ║ 사이의 표시 폭

    private final List<String> logs = Collections.synchronizedList(new LinkedList<>());

    public synchronized void addLog(String message) {
        logs.add(message);
        while (logs.size() > MAX_LOGS) {
            logs.remove(0);
        }
    }

    /**
     * 문자열의 터미널 표시 폭을 계산한다.
     * - 한글, CJK 문자, 이모지 → 2칸
     * - 그 외 → 1칸
     * - Variation Selector(U+FE0F) 등 폭 0 문자 제외
     */
    private int displayWidth(String s) {
        int width = 0;
        int len = s.length();
        for (int i = 0; i < len;) {
            int cp = s.codePointAt(i);
            i += Character.charCount(cp);

            // 폭 0인 문자는 건너뜀 (Variation Selector 등)
            if (cp == 0xFE0F || cp == 0xFE0E || cp == 0x200D) {
                continue;
            }

            if (isWideChar(cp)) {
                width += 2;
            } else {
                width += 1;
            }
        }
        return width;
    }

    /**
     * 터미널에서 2칸을 차지하는 문자인지 판별
     */
    private boolean isWideChar(int cp) {
        // 한글 자모, 한글 음절
        if (cp >= 0x1100 && cp <= 0x11FF)
            return true;
        if (cp >= 0xAC00 && cp <= 0xD7AF)
            return true;
        if (cp >= 0x3130 && cp <= 0x318F)
            return true;

        // CJK
        if (cp >= 0x2E80 && cp <= 0x9FFF)
            return true;
        if (cp >= 0xF900 && cp <= 0xFAFF)
            return true;

        // 전각 문자
        if (cp >= 0xFF01 && cp <= 0xFF60)
            return true;
        if (cp >= 0xFFE0 && cp <= 0xFFE6)
            return true;

        // 블록 문자 (█, ░ 등)는 Windows 터미널에서 1칸이므로 포함하지 않음

        // 이모지 범위
        if (cp >= 0x1F300 && cp <= 0x1F9FF)
            return true; // Misc Symbols, Emoticons, etc.
        if (cp >= 0x2600 && cp <= 0x27BF)
            return true; // Misc Symbols, Dingbats
        if (cp >= 0x1FA00 && cp <= 0x1FA6F)
            return true; // Chess symbols, extended-A
        if (cp >= 0x1FA70 && cp <= 0x1FAFF)
            return true; // Symbols extended-A
        if (cp >= 0x2694 && cp <= 0x2694)
            return true; // ⚔
        if (cp >= 0x1F600 && cp <= 0x1F64F)
            return true; // Emoticons

        // Box drawing 문자 (═, ║ 등)는 1칸이므로 여기에 포함하지 않음

        return false;
    }

    /**
     * 문자열을 터미널 표시 폭 기준으로 오른쪽 패딩하여 targetWidth 폭에 맞춘다.
     */
    private String padRight(String s, int targetWidth) {
        int dw = displayWidth(s);
        int padding = targetWidth - dw;
        if (padding <= 0)
            return s;
        return s + " ".repeat(padding);
    }

    // 오른쪽 ║ 이 위치할 컬럼 (╔ 1칸 + 내부 54칸 + ╗ = 56번째 컬럼)
    private static final int RIGHT_BORDER_COL = BOX_INNER_WIDTH + 2;

    /**
     * 내용을 ║ ... ║ 형태로 감싼다.
     * ANSI 이스케이프 \033[<col>G 로 오른쪽 ║ 를 고정 컬럼에 배치하여
     * 이모지 폭 차이에 상관없이 항상 정렬된다.
     */
    private String boxLine(String content) {
        return "║" + content + "\033[" + RIGHT_BORDER_COL + "G║";
    }

    public void render(Enemy boss, Enemy mob1, Enemy mob2, List<Hero> heroes) {
        StringBuilder sb = new StringBuilder();

        // ANSI: 커서를 홈으로 이동 + 화면 클리어
        sb.append("\033[H\033[2J");
        sb.append("\033[0m");

        String border = "═".repeat(BOX_INNER_WIDTH);

        // 타이틀
        sb.append("╔").append(border).append("╗\n");
        sb.append(boxLine("            ⚔\uFE0F   B O S S   R A I D   ⚔\uFE0F")).append("\n");
        sb.append("╠").append(border).append("╣\n");

        // 적 상태
        sb.append(formatEnemy("👹 Boss  ", boss)).append("\n");
        sb.append(formatEnemy("👾 Mob 1 ", mob1)).append("\n");
        sb.append(formatEnemy("👾 Mob 2 ", mob2)).append("\n");

        sb.append("╠").append(border).append("╣\n");

        // 영웅 상태
        sb.append("║  🛡\uFE0F  영웅 상태                                        ║\n");
        sb.append("║                                                      ║\n");

        int half = (heroes.size() + 1) / 2;
        for (int i = 0; i < half; i++) {
            String left = formatHero(i, heroes.get(i));
            String right = "";
            if (i + half < heroes.size()) {
                right = formatHero(i + half, heroes.get(i + half));
            }
            String leftPadded = padRight(left, 24);
            String rightPadded = padRight(right, 23);
            sb.append(boxLine("  " + leftPadded + " │  " + rightPadded)).append("\n");
        }

        sb.append("╠").append(border).append("╣\n");

        // 전투 로그
        sb.append(boxLine("  📜 전투 로그")).append("\n");

        List<String> snapshot;
        synchronized (logs) {
            snapshot = new ArrayList<>(logs);
        }

        for (int i = 0; i < MAX_LOGS; i++) {
            if (i < snapshot.size()) {
                String log = snapshot.get(i);
                // 로그가 너무 길면 자르기 (표시 폭 기준)
                log = truncateToDisplayWidth(log, 49);
                sb.append(boxLine("  > " + padRight(log, 49))).append("\n");
            } else {
                sb.append(boxLine("")).append("\n");
            }
        }

        sb.append("╚").append(border).append("╝\n");

        System.out.print(sb.toString());
        System.out.flush();
    }

    public void renderResult(String resultMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("═".repeat(BOX_INNER_WIDTH)).append("\n");
        sb.append("  ").append(resultMessage).append("\n");
        sb.append("═".repeat(BOX_INNER_WIDTH)).append("\n");
        System.out.print(sb.toString());
        System.out.flush();
    }

    private String formatEnemy(String label, Enemy enemy) {
        long maxHp = enemy.getMaxHp();
        long currentHp = Math.max(0, enemy.getHp());
        boolean alive = enemy.getAlive();

        if (!alive) {
            String status = "💀 DEAD";
            String content = "  " + label + "  [" + " ".repeat(BAR_WIDTH) + "]  " + status;
            return boxLine(content);
        }

        int filled = (int) ((double) currentHp / maxHp * BAR_WIDTH);
        filled = Math.max(0, Math.min(BAR_WIDTH, filled));
        int empty = BAR_WIDTH - filled;

        String bar = "█".repeat(filled) + "░".repeat(empty);
        String status = String.format("%4d/%d HP", currentHp, maxHp);
        String content = "  " + label + "  [" + bar + "]  " + padRight(status, 13);
        return boxLine(content);
    }

    private String formatHero(int index, Hero hero) {
        String icon = hero.getAlive() ? "💚" : "💀";
        long hp = Math.max(0, hero.getHp());
        return String.format("%s Hero %02d: %3d HP", icon, index + 1, hp);
    }

    /**
     * 문자열을 표시 폭 기준으로 maxWidth까지 자른다.
     */
    private String truncateToDisplayWidth(String s, int maxWidth) {
        int width = 0;
        int len = s.length();
        for (int i = 0; i < len;) {
            int cp = s.codePointAt(i);
            int charLen = Character.charCount(cp);
            int charWidth = 0;
            if (cp == 0xFE0F || cp == 0xFE0E || cp == 0x200D) {
                charWidth = 0;
            } else if (isWideChar(cp)) {
                charWidth = 2;
            } else {
                charWidth = 1;
            }

            if (width + charWidth > maxWidth - 3) {
                return s.substring(0, i) + "...";
            }
            width += charWidth;
            i += charLen;
        }
        return s;
    }
}
