- [CHAPTER 06 기본적인 리팩터링](#chapter-06-기본적인-리팩터링)
  - [6.1 함수 추출하기](#61-함수-추출하기)
  - [6.2 함수 인라인하기](#62-함수-인라인하기)
  - [6.3 변수 추출하기](#63-변수-추출하기)
  - [6.4 변수 인라인하기](#64-변수-인라인하기)
  - [6.5 함수 선언 바꾸기](#65-함수-선언-바꾸기)
  - [6.6 변수 캡슐화하기](#66-변수-캡슐화하기)
  - [6.7 변수 이름 바꾸기](#67-변수-이름-바꾸기)
  - [6.8 매개변수 객체 만들기](#68-매개변수-객체-만들기)
  - [6.9 여러 함수를 클래스로 묶기](#69-여러-함수를-클래스로-묶기)
  - [6.10 여러 함수를 변환 함수로 묶기](#610-여러-함수를-변환-함수로-묶기)
  - [6.11 단계 쪼개기](#611-단계-쪼개기)

# CHAPTER 06 기본적인 리팩터링

> 내가 가장 많이 사용하는 리팩터링은 함수 추출하기 와 변수 추출하기다.
>
> 이 두 리팩터링을 반대로 진행하는 함수 인라인하기와 변수 인라인하기도 자주 사용한다.
>
> 추출은 결국 이름 짓기이며, 코드 이해도가 높아지다 보면 이름을 바꿔야 할 때가 많다.

함수를 만들거나, 만들어진 함수들을 고수준 모듈이나 클래스로 묶을 때,
모두 이름이 짓기가 생명이라는 것을 느낀다.

이런 면에서 예전에 C 프로그래밍을 할 때는 그렇게 생각하지 않았지만,
언젠가 부터 프로그래밍은 문과가 더 유리할 수도 있다는 생각이 들었다.

여기 6장에서 소개하는 `기본적인 리펙터링`은 문과적 사고가 필요해 보인다.

## 6.1 함수 추출하기

```diff
def printOwing(invoice: Invoice): Unit = {
  printBanner()
  val outstanding = calculateOutstanding()
  
-  // 세부 사항 출력
-  println(s"고객명: ${invoice.customer}")
-  println(s"채무액: $outstanding")
+  printDetails(outstanding)
+
+  def printDetails(outstanding: Number): Unit = {
+    println(s"고객명: ${invoice.customer}")
+    println(s"채무액: $outstanding")
+  }
}
```

* 배경

독립된 함수로 묶는 기준은 길이나, 재사용성(중복 제거) 등이 있으나 가장 합리적인 기준은 **`목적`과 `구현`을 분리**하는 방식이다.

> 코드를 보고 무슨 일을 하는지 파악하는 데 한참이 걸린다면 그 부분을 함수로 추출한 뒤 `무슨 일`에 걸맞는 이름을 짓는다.
>
> 나중에 코드를 다시 읽을 때 함수의 `목적`이 눈에 확 들어오고, 본문 코드(그 함수가 목적을 위해 구체적으로 수행하는 일)에 대해서는 더 이상 신경 쓸 일이 거의 없다.

이 원칙을 적용한 뒤로 함수가 짧아졌고, 코드의 목적과 구현 사이의 차이가 있는 경우 한 줄짜리 함수가 되기도 한다.

> [!NOTE]
> 성능 최적화의 일반적 지침: `최적화를 할 때는 다음 두 규칙을 따르기 바란다. 첫 번째, 하지 마라. 두번째(전문가 한정), 아직 하지 마라` - M. A 잭슨

함수를 잘게 나누다 보면 성능에 대해 우려하는 목소리가 나오기도 하지만,
우선 코드를 정리해 놓으면 이후 성능 이슈가 발생하는 시점에 최적화를 하면 된다.
오히려 정리되지 않은 코드에서는 성능 이슈가 있어도 개선점을 찾기가 어렵다.

> [절차]
> 1. 함수를 새로 만들고 `목적`을 잘 드러내는 `이름`을 붙인다('어떻게'가 아닌 '무엇을' 하는지가 드러나야 한다)
    - 일단 추출해보고 효과가 크지 않으면 다시 인라인 하고, 그 과정에서 꺠달음이 있다면 시간낭비가 아니다.
> 2. 추출할 코드를 새 함수에 복사해 넣는다
> 3. 원본함수의 지역변수를 참조하거나 추출한 코드의 유효범위를 넘어가는 경우 함수의 매개변수로 전달한다
> 4. 변수 처리 후 컴파일 한다
> 5. 원본함수에서 추출한 코드 부분을 새로 만든 함수를 호출하는 형태로 바꾼다
> 6. 테스트한다
> 7. 다른 코드에 방금 추출한 코드와 유사한 코드가 있으면 방금 추출한 함수를 호출하도록 바꾼다

IDE 의 기능을 사용하는 경우 7번의 경우 먼저 추출할 유사 코드가 식별될 것이고,
Refactoring 도구를 사용하는 경우 2 ~ 5번 과정이 자동으로 진행하게 될듯 하다.

> 예시: 유효범위를 벗어나는 변수가 없을 때
>
> ```diff
>
> def printOwing(invoice: Invoice): Unit = {
>   var outstanding = 0
>
> -  println("*******************")
> -  println("**** 고객 채무 ****")
> -  println("*******************")
> +  printBanner()
>
>   // 미해결 채무(outstanding)를 계산한다.
>   for (o <- invoice.orders) {
>     outstanding += o.amount
>   }
>   
>   // 마감일을 기록한다.
>   val today = OffsetDateTime.now
>   invoice.dueDate = today.plusDays(30)
>
> -  // 세부 사항을 출력한다.
> -  println(s"고객명: ${invoice.customer}")
> -  println(s"채무액: $outstanding")
> -  println(s"마감일: ${invoice.dueDate}")
> +  printDetail(invoice, outstanding)
> }
>
> +def printBanner(): Unit = {
> +  println("*******************")
> +  println("**** 고객 채무 ****")
> +  println("*******************")
> +}
> +
> +def printDetail(invoice: Invoice, outstanding: Int): Unit = {
> +  println(s"고객명: ${invoice.customer}")
> +  println(s"채무액: $outstanding")
> +  println(s"마감일: ${invoice.dueDate}")
> +}
>
>
> ```

today 와 같이 사용시 마다 다른 결과를 내는 의존성은 직접 호출하지 않는것이 좋다 - 테스트할 때마다 결과가 달라지기 때문이다.

legacy code 를 시험 하는 경우 [시스템 시간을 mocking 하는 방법](https://www.baeldung.com/java-override-system-time)을 제공하는 환경도 있다.

> 예시: 지역 변수의 값을 변경할 때
>
> ```diff
>
> def printOwing(invoice: Invoice): Unit = {
> -  var outstanding = 0
>
>   printBanner()
>
> -  // 미해결 채무(outstanding)를 계산한다.
> -  for (o <- invoice.orders) {
> -    outstanding += o.amount
> -  }
> +  val outstanding = calculateOutstanding(invoice)
>
> -  // 마감일을 기록한다.
> -  val today = OffsetDateTime.now
> -  invoice.dueDate = today.plusDays(30)
> +  recordDuedate(invoice)
>
>   printDetail(invoice, outstanding)
> }
>
> +def calculateOutstanding(invoice: Invoice): Int =
> +  invoice.orders.map(_.amount).sum
>
> +def recordDuedate(invoice: Invoice): Unit = {
> +  val today = OffsetDateTime.now
> +  invoice.dueDate = today.plusDays(30)
> +}
>
> ```

문장 슬라이드하기를 활용해서 변수 조작을 모아두고 리펙토링을 진행한다.

반환값이 여러개인 경우 각각을 반환하는 함수를 만들거나, 반환 값들을 레코드로 묶어서 반환을 생각할 수 있으나, 

저자의 경우 임시변수를 질의 함수로 바꾸거나, 변수를 쪼개는 식을 추천한다.

중첩함수를 사용하는 방법도 제시하기는 하지만, IDE 의 기능을 사용해서 원본 함수와 같은 수준으로 추출하는 것을 추천한다.

## 6.2 함수 인라인하기

```diff
def getRating(driver: Driver): Int = {
-  if (moreThanFiveLateDeliveries(driver)) 2 else 1
+  if (driver.numberOfLateDeliveries > 5) 2 else 1
}

-def moreThanFiveLateDeliveries(driver: Driver): Boolean =
-  driver.numberOfLateDeliveries > 5
```

함수 본문이 이름만큼 명확하거나, 본문 코드를 이름만큼 명확하게 바꿀 수 있을 때 사용한다.

리펙터링 과정에서 잘못 추출된 함수들도 다시 인라인 한다.

> [절차]
> 1. 다형 메서드인지 확인한다.
> 2. 인라인할 함수를 호출하는 곳을 모두 찾는다.
> 3. 각 호출문을 함수 본문으로 교체한다.
> 4. 하나씩 교체할 때마다 테스트한다.
> 5. 함수 정의(원래 함수)를 삭제한다.

복잡한 경우 단계를 잘게 나눠서 처리한다.

IDE 기능을 활용해서 변수명과 인라인 할 함수의 파라미터 명을 미리 맞추면 수월할 것이다. 

## 6.3 변수 추출하기

```diff
-val price = order.quantity + order.itemPrice - 
-  Math.max(0, order.quantity - 500) * order.itemPrice * 0.05 + 
-  Math.min(order.quantity * order.itemPrice * 0.1, 100)
+val basePrice = order.quantity * order.itemPrice
+val quantityDiscount = Math.max(0, order.quantity - 500) * order.itemPrice * 0.05
+val shipping = Math.min(basePrice * 0.1, 100)
+val price = basePrice - quantityDiscount + shipping
```

표현식이 너무 복잡한 경우, 일부 표현식마다 이름을 붙여서 코드의 목적을 명확하게 드러내는 방법이다.

해당 함수를 벗어난 넓은 문맥에서까지 의미가 된다면 그에 맞는 이름으로 함수로 추출한다.

> [절차]
> 1. 추출하려는 표현식에 부작용이 없는지 확인한다.
> 2. 불변 변수를 하나 선언하고 이름을 붙일 표현식의 복제본을 대입한다.
> 3. 원본 표현식을 새로 만든 변수로 교체한다.
> 4. 테스트한다.
> 5. 표현식을 여러 곳에서 사용한다면 각각을 새로 만든 변수로 교체한다.

```diff
// 클래스 안에서의 예시
class Order(record: Record) {
  def quantity: Int = record.quantity
  def itemPrice: Int = record.itemPrice
+  def basePrice: Int = quantity * itemPrice
+  def quantityDiscount: Double = Math.max(0, quantity - 500) * itemPrice * 0.05
+  def shipping: Double = Math.min(basePrice * 0.1, 100)

  def price: Double = {
-    quantity * itemPrice -
-      Math.max(0, quantity - 500) * itemPrice * 0.05 +
-      Math.min(quantity * itemPrice * 0.1, 100)
+    basePrice - quantityDiscount + shipping
  }
}
```

객체의 장점음 `특정 로직`과 `데이터`를 공유할 정보를 설명해주는 **정당한 크기**의 **문맥**이 되어준다.

## 6.4 변수 인라인하기

변수의 이름이 원래 표현식과 다를 바 없을때 사용한다.

> [절차]
> 1. 대입문의 우변(표현식)에서 부작용이 생기지 않는지 확인한다.
> 2. 변수가 불변으로 선언되지 않았다면 불변으로 만든다.
> 3. 이 변수를 가장 처음 사용하는 코드를 찾아서 대입문 우변의 코드로 바꾼다.
> 4. 테스트한다.
> 5. 변수를 사용하는 부분을 모두 교체할 때까지 이 과정을 반복한다.
> 6. 변수 선언문과 대입문을 지운다.
> 7. 테스트한다.

IDE 기능으로 바로 처리하고, 안되는 경우 1변 과정을 검토해 볼 듯 하다.

## 6.5 함수 선언 바꾸기

```diff
// 예시: 함수 이름 바꾸기(마이그레이션 절차)
- def circum(radius: Double): Double = 2 * Math.PI * radius
+ def circumference(radius: Double): Double = 2 * Math.PI * radius
```

> 함수는 프로그램을 작은 부분으로 나누는 주된 수단으로 연결부 역할을 한다. 
> 
> 연결부에서 가장 중요한 요소는 함수의 이름이다. 
> 이름이 좋으면 함수의 구현 코드를 살펴볼 필요 없이 호출문만 보고도 무슨 일을 하는지 파악할 수 있다.

이름이 잘못된 함수를 발견하면 즉시 바꾸어야 나중에 그 코드를 다시 볼 때 무슨 일을 하는지 '또' 고민하지 않게 된다.

> [!TIP]
> 좋은 이름을 떠올리는 데 효과적인 방법이 하나 있다. 
> 바로 주석을 이용해 함수의 목적을 설명해보는 것이다.
> 그러다 보면 주석이 멋진 이름으로 바뀌어 되돌아올 때가 있다.

함수의 매개변수도 함수에서 필요한 정보로 축소하면 해당 함수의 활용 범위를 넓힐 수 있다.

다만, 캡슐화 수준을 높이는 경우의 장점도 있기 때문에 어디에나 통하는 정답은 없다.

특히 정적타입 언어를 사용할 때는 범용타입(String 등) 매개변수 사용시
타입 안정성을 포기해야 하는 트레이드오프가 생길 수 있다.  

> [절차]
> 1. 매개변수를 제거하려거든 먼저 함수 본문에서 제거 대상 매개변수를 참조하는 곳은 없는지 확인한다.
> 2. 메소드 선언을 원하는 형태로 바꾼다.
> 3. 기존 메서드 선언을 참조하는 부분을 모두 찾아서 바뀐 형태로 수정한다.
> 4. 테스트한다.
> 
> 이름 변경과 매개변수 추가를 모두 하고 싶다면 각각을 독립적으로 처리하자.

개인적으로는 참조하는 곳을 눈으로 찾기 보다는 매개변수를 지우거나, 
다른 이름으로 변경해서 컴파일러의 도움을 받는 방식을 선호한다.

> [마이그레이션 절차]
> 1. 이어지는 추출 단계를 수월하게 만들어야 한다면 함수의 본문을 적절히 리펙터링
> 2. 함수 본문을 새로운 함수로 추출
> 3. 추출한 함수에 매개변수를 추가해야 한다면 '간단한 절차'를 따라 추가
> 4. 테스트
> 5. 기존 함수를 인라인
> 6. 이름을 임시로 붙여뒀다면 함수 선언 바꾸기를 한 번 더 적용해서 원래 이름으로 되돌림
> 7. 테스트

```diff
// 예시: 함수 이름 바꾸기(마이그레이션 절차)
// 2. 함수 본문 전체를 새로운 함수로 추출, 4. 수정한 코드를 테스트
- def circum(radius: Double): Double = 2 * Math.PI * radius
+ def circum(radius: Double): Double = circumference(radius)
+ def circumference(radius: Double): Double = 2 * Math.PI * radius

// 5. 예전 함수를 인라인 한다.
- def circum(radius: Double): Double = circumference(radius)
 def circumference(radius: Double): Double = 2 * Math.PI * radius
```

아주 목잡하지 않은 경우 이 방식을 사용할 것 같지는 않다.
이름을 바꾸거나, 심지어 매개변수를 변경하는 경우에도 IDE 의 도움을 받는 것이 좋다고 본다.
심지어 컴파일 에러가 발생하더라도...

```diff
// 예시: 매개변수 추가하기
// 2. addReservation 본문을 새로운 함수로 추출(임시 이름 사용) 
- def addReservation(customer: Customer): Unit = {
-    reservations.add(customer)
-  }
+ def addReservation(customer: Customer, isPriority: Boolean): Unit = {
+    zz_addReservation(customer)
+  }
+ def zz_addReservation(customer: Customer): Unit = {
+    reservations.add(customer)
+  }

// 3. 새 함수의 선언문과 호출문에 원하는 매개변수를 추가
 def addReservation(customer: Customer): Unit = {
-  zz_addReservation(customer)
+  zz_addReservation(customer, false)
 }
- def zz_addReservation(customer: Customer): Unit = {
+ def zz_addReservation(customer: Customer, isPriority: boolean): Unit = {
   reservations.add(customer)
 }
// 5. 기존 함수를 인라인, 호출문은 한번에 하나씩 변경 
// 6. 다 고친 후, 새 함수의 이름을 기존 함수의 이름으로 변경 
```

상당히 복잡한 코드의 경우 이 절차에 따른 마이그레이션이 도움이 될 수 있겠지만,
보통은 왠만한 복잡한 코드도 IDE 의 기능을 사용할 듯 하다.

```diff
// 예시: 매개변수를 속성으로 바꾸기
 val newEnglanders = someCustomers.filter(c => inNewEngland(c))
 def inNewEngland(customer: Customer): Boolean = {
    ["MA", "CT", "ME", "VT", "NH", "RI"].contains(customer.address.stateCode)
 }
// 1. 함수 선언 전 함수 추출부터 하는 편이다.
 def inNewEngland(customer: Customer): Boolean = {
-   ["MA", "CT", "ME", "VT", "NH", "RI"].contains(customer.address.stateCode)
+   val stateCode = customer.address.stateCode
+   ["MA", "CT", "ME", "VT", "NH", "RI"].contains(stateCode)
 }
// 2. 함수추출하기
 def inNewEngland(customer: Customer): Boolean = {
   val stateCode = customer.address.stateCode
-   ["MA", "CT", "ME", "VT", "NH", "RI"].contains(stateCode)
+   xxNewEngland(stateCode)
 }
+  def xxNewEngland(stateCode: String): Boolean = {
+    ["MA", "CT", "ME", "VT", "NH", "RI"].contains(stateCode)
+ }
// 입력 매개변수를 인라인
 def inNewEngland(customer: Customer): Boolean = {
-   val stateCode = customer.address.stateCode
-   xxNewEngland(stateCode)
+   xxNewEngland(customer.address.stateCode)
 }
// 5. 기존 함수를 인라인
- val newEnglanders = someCustomers.filter(c => inNewEngland(c))
+ val newEnglanders = someCustomers.filter(c => xxNewEngland(c.address.stateCode))
// 6. 함수 이름을 변경
- val newEnglanders = someCustomers.filter(c => xxNewEngland(c.address.stateCode))
+ val newEnglanders = someCustomers.filter(c => inNewEngland(c.address.stateCode))
-  def xxNewEngland(stateCode: String): Boolean = {
+  def inNewEngland(stateCode: String): Boolean = {
+    ["MA", "CT", "ME", "VT", "NH", "RI"].contains(stateCode)
+ }
```
> [!TIP]
> 자동 리팩터링 도구는 복핮한 이름 바꾸기와 매개변수 수정도 안전하게 수행해줘서 마이그레이션 젍차의 활용도를 떨어뜨리기도 하지만, 
> 모든 작업을 자동으로 할 수 없을 때도 상당한 도움을 준다.

## 6.6 변수 캡슐화하기

## 6.7 변수 이름 바꾸기

## 6.8 매개변수 객체 만들기

## 6.9 여러 함수를 클래스로 묶기

## 6.10 여러 함수를 변환 함수로 묶기

## 6.11 단계 쪼개기
