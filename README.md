![boss-raid_final](https://github.com/user-attachments/assets/38e887b7-e92f-4a68-b7b6-c2b7679c2ddb)

# [우리 FIS 아카데미 6기] Multi Thread Toy Project

> **멀티 스레드 이해를 위한 보스 레이드 토이 프로젝트**<br>
> 이 프로젝트는 멀티 스레드 환경에서의 스레드 동작을 이해하기 위한 프로젝트입니다.<br>
> 프로젝트를 통해 각 스레드들이 메시지 큐나 `synchronized` 메소드를 이용해 통신하는 모습을 볼 수 있습니다.


## 프로젝트 설명
Enemy, Hero, Healer로 구성되는 각각의 스레드들이 서로 통신하며 상태를 갱신하는 모습을 TUI를 통해 게임처럼 확인할 수 있습니다.

메시지 큐와 `synchronized` 메소드를 사용해 각 스레드들이 어떻게 통신하는지 쉽게 알아볼 수 있도록 프로젝트를 구성했습니다.

## ✨ 핵심 기능
* **메시지 큐** : `MessageQueue` 클래스를 통해 구현
* **TUI(Text User Interface)** : CLI 환경에서 실행되는 모습을 보기 쉽게 `GameRenderer` 클래스를 통해 구현

## 🚀 시작하기

### Windows
```shell
chcp 65001; javac "-encoding UTF-8" -d bin src/main/java/dev/raid/*.java; java -cp bin "-Dfile.encoding=UTF-8" dev.raid.Main
```

### Mac
```shell
javac -d bin src/main/java/dev/raid/*.java && java -cp bin dev.raid.Main
```

## ⚙️ 시스템 동작 흐름
이 프로젝트는 각 스레드가 서로 통신하며 각자의 필드를 조작하며 동작합니다.

### 1. 스레드 생성
`Main`클래스에서 각 클래스들을 생성합니다. 데몬 스레드로 설정하여 메인 스레드가 종료될 때 함께 종료되도록 합니다. 순환문을 돌면서 한 쪽이 전멸하게 되면 메인 스레드를 종료하도록 구현했습니다.

### 2. 스레드 통신
`MessageQueue`를 통해서 `Hero`스레드와 `Enemy`스레드가 통신합니다. `Hero`스레드들이 메시지 큐에 가할 데미지가 담긴 메시지를 enqueue하면 `Enemy`스레드는 그 메시지를 dequeue하여 체력을 소모합니다.  
`Enemy`스레드가 `Hero`스레드에 가하는 데미지와 `Healer`스레드가 `Hero`스레드에게 회복하는 회복량은 메시지 큐가 아닌 `Hero`클래스의 `synchronized`메소드를 이용해서 전달됩니다. 이는 `Hero`스레드보다 다른 스레드의 수가 적어서 dequeue에서 대기해야 하는 시간이 매우 길어지기 때문입니다.
