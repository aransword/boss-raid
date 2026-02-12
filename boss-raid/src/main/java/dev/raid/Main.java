package dev.raid;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		MessageQueue messageQueue = new MessageQueue();

		Boss boss = new Boss(1000, 10, messageQueue);
		List<Hero> heroes = new ArrayList<>();
		Thread[] heroThread = new Thread[5];

		for (int i = 0; i < 5; i++) {
			heroes.add(new Hero(100, 10, messageQueue));
			heroThread[i] = new Thread(heroes.get(i)::action);
			heroThread[i].setDaemon(true);
		}

		Healer healer = new Healer(100, 5, heroes);

		Thread bossThread = new Thread(boss::action);
		Thread healerThread = new Thread(healer::action);
		Thread healerThread2 = new Thread(healer::action);
		Thread healerThread3 = new Thread(healer::action);

		// 데몬 스레드: 메인 스레드 종료 시 자동으로 같이 종료됨
		;
		healerThread.setDaemon(true);
		healerThread2.setDaemon(true);
		healerThread3.setDaemon(true);

		for (int i = 0; i < 5; i++) {
			heroThread[i].start();
		}
		healerThread.start();
		healerThread2.start();
		healerThread3.start();

		bossThread.start();

		bossThread.join(); // 보스 스레드가 끝날 때까지 대기 (busy-wait 대신)
		System.out.println("Boss is dead");
	}

}
