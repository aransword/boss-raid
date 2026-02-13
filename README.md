![boss-raid_final](https://github.com/user-attachments/assets/38e887b7-e92f-4a68-b7b6-c2b7679c2ddb)
<img width="853" height="1000" alt="image" src="https://github.com/user-attachments/assets/5e73a778-3bcf-4c00-aad6-68675d758a4d" />

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

## ⚙️ 시스템 설명
이 프로젝트는 각 스레드가 서로 통신하며 각자의 필드를 조작하며 동작합니다.

### 1. 스레드 생성
`Main`클래스에서 각 클래스들을 생성합니다. 데몬 스레드로 설정하여 메인 스레드가 종료될 때 함께 종료되도록 합니다. 순환문을 돌면서 한 쪽이 전멸하게 되면 메인 스레드를 종료하도록 구현했습니다.

### 2. 스레드 통신
`MessageQueue`를 통해서 `Hero`스레드와 `Enemy`스레드가 통신합니다. `Hero`스레드들이 메시지 큐에 가할 데미지가 담긴 메시지를 enqueue하면 `Enemy`스레드는 그 메시지를 dequeue하여 체력을 소모합니다.  
`Enemy`스레드가 `Hero`스레드에 가하는 데미지와 `Healer`스레드가 `Hero`스레드에게 회복하는 회복량은 메시지 큐가 아닌 `Hero`클래스의 `synchronized`메소드를 이용해서 전달됩니다. 이는 `Hero`스레드보다 다른 스레드의 수가 적어서 dequeue에서 대기해야 하는 시간이 매우 길어지기 때문입니다.  

## 고찰[boss-raid_final](https://github.com/user-attachments/assets/38e887b7-e92f-4a68-b7b6-c2b7679c2ddb)
<img width="853" height="1000" alt="image" src="https://github.com/user-attachments/assets/5e73a778-3bcf-4c00-aad6-68675d758a4d" />

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

## ⚙️ 시스템 설명
이 프로젝트는 각 스레드가 서로 통신하며 각자의 필드를 조작하며 동작합니다.

### 1. 스레드 생성
`Main`클래스에서 각 클래스들을 생성합니다. 데몬 스레드로 설정하여 메인 스레드가 종료될 때 함께 종료되도록 합니다. 순환문을 돌면서 한 쪽이 전멸하게 되면 메인 스레드를 종료하도록 구현했습니다.

### 2. 스레드 통신
`MessageQueue`를 통해서 `Hero`스레드와 `Enemy`스레드가 통신합니다. `Hero`스레드들이 메시지 큐에 가할 데미지가 담긴 메시지를 enqueue하면 `Enemy`스레드는 그 메시지를 dequeue하여 체력을 소모합니다.  
`Enemy`스레드가 `Hero`스레드에 가하는 데미지와 `Healer`스레드가 `Hero`스레드에게 회복하는 회복량은 메시지 큐가 아닌 `Hero`클래스의 `synchronized`메소드를 이용해서 전달됩니다. 이는 `Hero`스레드보다 다른 스레드의 수가 적어서 dequeue에서 대기해야 하는 시간이 매우 길어지기 때문입니다.  

## 🔍 고찰

### 1. 메시지 큐
프로세스 간의 데이터를 교환할 때 사용되는 통신 방법의 일종입니다. 생산자가 메시지를 생산해서 큐에 offer하면, 소비자는 해당 큐를 poll해서 그 메시지를 처리하게 되는 구조를 가지고 있습니다. 프로젝트에서는 Hero 스레드가 생산자, Enemy 스레드가 소비자 역할을 수행합니다. Hero 스레드들이 가할 데미지를 메시지에 담아서 큐에 offer하면 Enemy 스레드들은 큐에서 poll을 통해서 데미지를 받은만큼 체력이 줄어드는 처리를 수행합니다.  
반대로 Enemy측에서 Hero에게 가하는 데미지와 Healer가 Hero에게 가하는 힐 또한 메시지 큐로 구현해보고자 했습니다. 하지만 그렇게 구현하게 되면 Hero가 poll을 하기 위해 메시지 큐에서 대기하는 동안 아무 행동도 취할 수 없게 된다는 것을 깨달았습니다. 메시지 큐는 생산자가 생산하는 메시지가 소비자에 비해 비교적 많을 때 효율적입니다. 따라서 Hero가 가하는 데미지를 제외한 나머지는 Hero 클래스에 구현된 synchronized 메소드를 각 스레드가 호출하게 하여 그 처리를 Hero 객체가 직접 수행하도록 했다.

### 2. 데몬 스레드
데몬 스레드는 다른 스레드보다 우선순위가 낮은 스레드입니다. JVM에 남은 일반 스레드의 개수가 0개가 되면 JVM은 데몬 스레드의 상태에 상관 없이 강제로 데몬 스레드들을 멈추게 합니다.  
프로젝트에서는 Hero, Healer, Enemy 스레드가 모두 데몬 스레드로 설정되었습니다. 각 스레드들은 자신의 HP를 기반으로 살아있는지를 체크하면서 run하므로, 승패가 결정된 이후에도 스레드가 종료되지 않습니다. 따라서 해당 스레드들을 데몬 스레드로 설정함으로써 메인 스레드가 승패를 결정하고 종료되면 데몬 스레드들이 전부 종료되게 됩니다.

### 3. `CopyOnWriteArrayList`
`CopyOnWriteArrayList`를 통해서 `Hero`객체 배열을 유지합니다. `CopyOnWriteArrayList`는 배열에 쓰기 연산이 들어오면 원래의 배열에 쓰는 것이 아니라 같은 내용의 새로운 배열을 생성해서 그 배열에 쓰기를 수행합니다. 쓰기가 완료되면 참조 위치를 새로 생성된 배열로 옮기게 됩니다. 이러한 특성 때문에 읽기 연산이 얼마나 많이 들어오든 쓰기 연산은 대기 없이 수행될 수 있게 됩니다.  
그러므로 `Healer`클래스의 removeIf에서 유지되는 `modCount`, 즉 수정 횟수에 따른 `ConcurrentModificationException`을 방지해줍니다.
