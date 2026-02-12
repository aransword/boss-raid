package dev.raid;

import java.util.ArrayList;
import java.util.List;

public class Main {
	private static final int HERO = 19;

	public static void main(String[] args) throws InterruptedException {
		MessageQueue messageQueue = new MessageQueue();
		
		List<Hero> heroes = new ArrayList<>();
		Thread[] heroThread = new Thread[HERO];
		for(int i = 0; i < HERO; i++) {
			heroes.add(new Hero(100, 10, messageQueue));
			heroThread[i] = new Thread(heroes.get(i)::action);
			heroThread[i].setDaemon(true);
		}
		
		Enemy boss = new Enemy(1000, 10, heroes, messageQueue);
		Enemy mob1 = new Enemy(300, 6, heroes, messageQueue);
		Enemy mob2 = new Enemy(500, 8, heroes, messageQueue);
		
		
		Thread bossThread = new Thread(boss::action);
		Thread mob1Thread = new Thread(mob1::action);
		Thread mob2Thread = new Thread(mob2::action);
		
		for(int i = 0; i < HERO; i++) {
			heroThread[i].start();
		}
		
		bossThread.start();
		mob1Thread.start();
		mob2Thread.start();

		while (true) {
            
            // A. 적 팀 생존 확인
            boolean isEnemyAlive = false;
            if(boss.getAlive() || mob1.getAlive() || mob2.getAlive()) {
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

            // --- 4. 종료 조건 판별 ---
            
            if (!isEnemyAlive) {
                System.out.println("\n==============================");
                System.out.println("🎉 VICTORY! 모든 적을 처치했습니다!");
                System.out.println("==============================");
                break; // 게임 루프 종료 -> 메인 종료
            }
            
            if (!isHeroAlive) {
                System.out.println("\n==============================");
                System.out.println("💀 GAME OVER... 영웅이 전멸했습니다.");
                System.out.println("==============================");
                break; // 게임 루프 종료 -> 메인 종료
            }

            // 너무 자주 검사하면 CPU 낭비하므로 1초마다 체크
            Thread.sleep(1000); 
        }
	}

}
