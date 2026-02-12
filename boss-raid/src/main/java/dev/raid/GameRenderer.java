package dev.raid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameRenderer {
    private static final int MAX_LOGS = 8;
    private static final int BAR_WIDTH = 20;

    private final List<String> logs = Collections.synchronizedList(new LinkedList<>());

    public synchronized void addLog(String message) {
        logs.add(message);
        while (logs.size() > MAX_LOGS) {
            logs.remove(0);
        }
    }

    public void render(Enemy boss, Enemy mob1, Enemy mob2, List<Hero> heroes) {
        StringBuilder sb = new StringBuilder();

        // ANSI: 커서를 홈으로 이동 + 화면 클리어
        sb.append("\033[H\033[2J");
        sb.append("\033[0m");

        String border = "══════════════════════════════════════════════════════";

        // 타이틀
        sb.append("╔").append(border).append("╗\n");
        sb.append("║            ⚔\uFE0F   B O S S   R A I D   ⚔\uFE0F                ║\n");
        sb.append("╠").append(border).append("╣\n");

        // 적 상태
        sb.append(formatEnemy("👹 Boss  ", boss)).append("\n");
        sb.append(formatEnemy("👾 Mob 1 ", mob1)).append("\n");
        sb.append(formatEnemy("👾 Mob 2 ", mob2)).append("\n");

        sb.append("╠").append(border).append("╣\n");

        // 영웅 상태 (2열)
        sb.append("║  🛡\uFE0F  영웅 상태                                       ║\n");
        sb.append("║                                                     ║\n");

        int half = (heroes.size() + 1) / 2;
        for (int i = 0; i < half; i++) {
            String left = formatHero(i, heroes.get(i));
            String right = "";
            if (i + half < heroes.size()) {
                right = formatHero(i + half, heroes.get(i + half));
            }
            sb.append(String.format("║  %-24s │  %-23s ║\n", left, right));
        }

        sb.append("╠").append(border).append("╣\n");

        // 전투 로그
        sb.append("║  📜 전투 로그                                        ║\n");

        List<String> snapshot;
        synchronized (logs) {
            snapshot = new ArrayList<>(logs);
        }

        for (int i = 0; i < MAX_LOGS; i++) {
            if (i < snapshot.size()) {
                String log = snapshot.get(i);
                if (log.length() > 49) {
                    log = log.substring(0, 46) + "...";
                }
                sb.append(String.format("║  > %-49s ║\n", log));
            } else {
                sb.append(String.format("║  %-51s  ║\n", ""));
            }
        }

        sb.append("╚").append(border).append("╝\n");

        System.out.print(sb.toString());
        System.out.flush();
    }

    public void renderResult(String resultMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("══════════════════════════════════════════════════════\n");
        sb.append("  ").append(resultMessage).append("\n");
        sb.append("══════════════════════════════════════════════════════\n");
        System.out.print(sb.toString());
        System.out.flush();
    }

    private String formatEnemy(String label, Enemy enemy) {
        long maxHp = enemy.getMaxHp();
        long currentHp = Math.max(0, enemy.getHp());
        boolean alive = enemy.getAlive();

        String status;
        if (!alive) {
            status = "💀 DEAD";
            return String.format("║  %s  [%-" + BAR_WIDTH + "s]  %-7s       ║", label, "", status);
        }

        int filled = (int) ((double) currentHp / maxHp * BAR_WIDTH);
        filled = Math.max(0, Math.min(BAR_WIDTH, filled));
        int empty = BAR_WIDTH - filled;

        String bar = "█".repeat(filled) + "░".repeat(empty);
        status = String.format("%4d/%d HP", currentHp, maxHp);

        return String.format("║  %s  [%s]  %-13s ║", label, bar, status);
    }

    private String formatHero(int index, Hero hero) {
        String icon = hero.getAlive() ? "💚" : "💀";
        long hp = Math.max(0, hero.getHp());
        return String.format("%s Hero %02d: %3d HP", icon, index + 1, hp);
    }
}
