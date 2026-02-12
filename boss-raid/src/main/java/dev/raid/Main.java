package dev.raid;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
	private static final int HERO = 14;

	public static void main(String[] args) throws Exception {
		// UTF-8 출력 설정 (JAR 실행 시에도 한글/이모지 깨짐 방지)
		System.setOut(new PrintStream(System.out, true, "UTF-8"));

		GameRenderer renderer = new GameRenderer();
		MessageQueue messageQueue = new MessageQueue();

		List<Hero> heroes = new CopyOnWriteArrayList<>();
		Thread[] heroThread = new Thread[HERO];
		for (int i = 0; i < HERO; i++) {
			heroes.add(new Hero(i + 1, 100, 10, messageQueue, renderer));
			heroThread[i] = new Thread(heroes.get(i)::action);
			heroThread[i].setDaemon(true);
		}

		Enemy boss = new Enemy("Boss", 1000, 10, heroes, messageQueue, renderer);
		Enemy mob1 = new Enemy("Mob 1", 300, 6, heroes, messageQueue, renderer);
		Enemy mob2 = new Enemy("Mob 2", 500, 8, heroes, messageQueue, renderer);

		Healer healer = new Healer(100, 5, heroes);

		Thread healerThread = new Thread(healer::action);
		Thread healerThread2 = new Thread(healer::action);
		Thread healerThread3 = new Thread(healer::action);

		// 데몬 스레드: 메인 스레드 종료 시 자동으로 같이 종료됨
		healerThread.setDaemon(true);
		healerThread2.setDaemon(true);
		healerThread3.setDaemon(true);

		Thread bossThread = new Thread(boss::action);
		Thread mob1Thread = new Thread(mob1::action);
		Thread mob2Thread = new Thread(mob2::action);

		// 보스와 몹도 데몬 스레드로 설정하여 메인 스레드 종료 시 함께 종료되도록 함
		bossThread.setDaemon(true);
		mob1Thread.setDaemon(true);
		mob2Thread.setDaemon(true);

		for (int i = 0; i < HERO; i++) {
			heroThread[i].start();
		}

		bossThread.start();
		mob1Thread.start();
		mob2Thread.start();

		healerThread.start();
		healerThread2.start();
		healerThread3.start();

		while (true) {
			// 화면 렌더링
			renderer.render(boss, mob1, mob2, heroes);

			// A. 적 팀 생존 확인
			boolean isEnemyAlive = false;
			if (boss.getAlive() || mob1.getAlive() || mob2.getAlive()) {
				isEnemyAlive = true;
			}

			// B. 영웅 팀 생존 확인
			boolean isHeroAlive = false;
			for (Hero h : heroes) {
				if (h.getAlive()) {
					isHeroAlive = true;
					break;
				}
			}

			// --- 종료 조건 판별 ---
			if (!isEnemyAlive) {
				renderer.render(boss, mob1, mob2, heroes);
				renderer.renderResult("🎉 VICTORY! 모든 적을 처치했습니다!");
				break;
			}

			if (!isHeroAlive) {
				renderer.render(boss, mob1, mob2, heroes);
				renderer.renderResult("💀 GAME OVER... 영웅이 전멸했습니다.");
				break;
			}

			// 200ms마다 화면 갱신
			Thread.sleep(200);
		}
	}
}
