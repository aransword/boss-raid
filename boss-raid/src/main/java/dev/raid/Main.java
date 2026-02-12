package dev.raid;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		MessageQueue messageQueue = new MessageQueue();
		Boss boss = new Boss(1000, 10, messageQueue);
		Hero hero = new Hero(100, 10, messageQueue);

		Thread bossThread = new Thread(boss::action);
		Thread heroThread1 = new Thread(hero::action);
		Thread heroThread2 = new Thread(hero::action);
		Thread heroThread3 = new Thread(hero::action);
		Thread heroThread4 = new Thread(hero::action);
		Thread heroThread5 = new Thread(hero::action);
		Thread heroThread6 = new Thread(hero::action);
		Thread heroThread7 = new Thread(hero::action);
		Thread heroThread8 = new Thread(hero::action);
		Thread heroThread9 = new Thread(hero::action);
		Thread heroThread10 = new Thread(hero::action);

		// 데몬 스레드: 메인 스레드 종료 시 자동으로 같이 종료됨
		heroThread1.setDaemon(true);
		heroThread2.setDaemon(true);
		heroThread3.setDaemon(true);
		heroThread4.setDaemon(true);
		heroThread5.setDaemon(true);
		heroThread6.setDaemon(true);
		heroThread7.setDaemon(true);
		heroThread8.setDaemon(true);
		heroThread9.setDaemon(true);
		heroThread10.setDaemon(true);

		heroThread1.start();
		heroThread2.start();
		heroThread3.start();
		heroThread4.start();
		heroThread5.start();
		heroThread6.start();
		heroThread7.start();
		heroThread8.start();
		heroThread9.start();
		heroThread10.start();

		bossThread.start();

		bossThread.join(); // 보스 스레드가 끝날 때까지 대기 (busy-wait 대신)
		System.out.println("Boss is dead");
	}

}
