![boss-raid_final](https://github.com/user-attachments/assets/38e887b7-e92f-4a68-b7b6-c2b7679c2ddb)

# [우리 FIS 아카데미 6기] Multi Thread Toy Project

> **멀티 스레드 이해를 위한 보스 레이드 토이 프로젝트**<br>
> 이 프로젝트는 멀티 스레드 환경에서의 스레드 동작을 이해하기 위한 프로젝트입니다.<br>
> 프로젝트를 통해 각 스레드들이 메시지 큐나 synchronized 메소드를 이용해 통신하는 모습을 볼 수 있습니다.


## 프로젝트 설명
Enemy, Hero, Healer로 구성되는 각각의 스레드들이 서로 통신하며 상태를 갱신하는 모습을 TUI를 통해 게임처럼 확인할 수 있습니다.

메시지 큐와 synchronized 메소드를 사용해 각 스레드들이 어떻게 통신하는지 쉽게 알아볼 수 있도록 프로젝트를 구성했습니다.

## ✨ 핵심 기능
* **메시지 큐** : MessageQueue 클래스를 통해 구현
* **TUI(Text User Interface)** : CLI 환경에서 실행되는 모습을 보기 쉽게 GameRenderer 클래스를 통해 구현

## 🚀 시작하기

### Windows
```shell
chcp 65001; javac "-encoding UTF-8" -d bin src/main/java/dev/raid/*.java; java -cp bin "-Dfile.encoding=UTF-8" dev.raid.Main
```

### Mac
```shell
javac -d bin src/main/java/dev/raid/*.java && java -cp bin dev.raid.Main
```
