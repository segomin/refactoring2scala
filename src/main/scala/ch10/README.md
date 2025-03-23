- [10.1 조건문 분해하기](#101-조건문-분해하기)
- [10.2 조건식 통합하기](#102-조건식-통합하기)
- [10.3 중첩 조건문을 보호 구문으로 바꾸기](#103-중첩-조건문을-보호-구문으로-바꾸기)
- [10.4 조건부 로직을 다형성으로 바꾸기](#104-조건부-로직을-다형성으로-바꾸기)
- [10.5 특이 케이스 추가하기](#105-특이-케이스-추가하기)
- [10.6 어서션 추가하기](#106-어서션-추가하기)
- [10.7 제어 플래그를 탈출문으로 바꾸기](#107-제어-플래그를-탈출문으로-바꾸기)

# CHAPTER 10 조건부 로직 간소화

> * 조건부 로직은 프로그램의 힘을 강화하는 데 크게 기여하지만,
> * 안타깝게도 프로그램을 복잡하게 만드는 주요 원흉이기도 하다.
> * 그래서 나는 조건부 로직을 이해하기 쉽게 바꾸는 리팩터링을 자주 한다.

- _pattern matching 을 사용하면 기존 switch 보다는 더 나은 코드를 만들 수 있다._
- _다형성 대신 사용할 경우 `switch + instance of` 로 보이지만,_
- _type 안정성과 유연함을 주기도 한다._
- _언어 별로 리팩터링이나 디자인 패턴의 차이가 있듯이 기술의 발전(언어의 버전)도 영향을 준다고 생각한다._

## 10.1 조건문 분해하기

```diff
- val charge = if (date.isBefore(plan.summerStart) || date.isAfter(plan.summerEnd)) {
-   quantity * plan.regularRate + plan.regularServiceCharge
- } else {
-   quantity * plan.summerRate
- }
+ val charge = if (summer()) summerCharge() else regularCharge()
```

> * 긴 함수는 그 자체로 읽기가 어렵지만, 조건문은 그 어려움을 한층 가중시킨다.
> * 거대한 코드는 덩어리들로 분해해서 `의도`를 살린 이름의 함수로 바꿔주자.
> * 그러면 전체적인 의도가 더 확실히 드러난다.
> * 조건식과 조건절에 이 작업을 해주면 조건이 무엇인지 강조되고, 무엇을 왜 분기했는지가 명백해진다.

* _함수형이나 리엑티브 코드를 잘 사용하면 코드를 간결하게 하는데 도움될 때가 있다._
* _다만, 함께 일하는 팀원의 수준을 고려해 모두가 이해 가능한 기술을 사용하라는 말도 있고,_
* _좋은 기술을 사용하기 위해 팀원의 수준을 높이라는 양반되는 의견도 있다._

## 10.2 조건식 통합하기

```diff
- if (employee.seiority < 2) return 0
- if (employee.monthDisabled > 12) return 0
- if (employee.isPartTime) return 0
+ if (isNotEligibleForDisability()) return 0
+ def isNotEligibleForDisability() = {
+   employee.seiority < 2 
+    || employee.monthDisabled > 12 
+    || employee.isPartTime
+ }
```

> * 조건부 코드를 통합하는 이유는
> * 내가 하려는 일이 더 명확해 진다
> * 이 작업이 함수 추출하기까지 이어질 수 있다.
> * 함수 추출하기는 `무엇`을 하는지를 기술하던 코드를 `왜` 하는지를 말해주는 코드로 바꿔준다.

## 10.3 중첩 조건문을 보호 구문으로 바꾸기

```diff
def getPayAmount() = {
-  var result = 0
-  if (isDead) result = deadAmount()
+  if (isDead) return deadAmount()
-  else {
-    if (isSeparated) result = separatedAmount()
+  if (isSeparated) return separatedAmount()
-    else {
-      if (isRetired) result = retiredAmount()
-      else result = normalPayAmount()
-    }
+  if (isRetired) return retiredAmount()
+  return normalPayAmount()
-  }
-  result
}
```

> [!NOTE]
> * 진입점이 하나라는 조건은 최신 프로그래밍 언어에서는 강제된다.
> * 그런데 반환점이 하나여야 한다는 규칙은 유용하지 않다.
> * 코드에서는 명확함이 핵심이다.
> * 반환점이 하나일 때 함수의 로직이 더 명백하다면 그렇게 하자.
> * 그렇지 않다면 하지 말자.

## 10.4 조건부 로직을 다형성으로 바꾸기

```diff
-  bird.kind match {
-    case "유럽 제비" => "보통이다"
-    case "아프리카 제비" => if (bird.numberOfCoconuts > 2) "지쳤다" else "보통이다"
-    case "노르웨이 파랑 앵무" => if (bird.voltage > 100) "그을렸다" else "예쁘다"
-    case _ => "알 수 없다"
-  }
+ class EuropeanSwallow() extends Bird("유럽 제비"):
+   override def plumage(): String = "보통이다"
+ class AfricanSwallow(val numberOfCoconuts: Int) extends Bird("아프리카 제비"):
+   override def plumage(): String = if numberOfCoconuts > 2 then "지쳤다" else "보통이다"
+ class NorwegianBlueParrot(val voltage: Int) extends Bird("노르웨이 파랑 앵무"):
+   override def plumage(): String = if voltage > 100 then "그을렸다" else "예쁘다"
```

> [!NOTE]
> * 다형성은 객체 지향 프로그래밍의 핵심이다. 하지만 남용하기 쉽다.
> * 모든 조건부 로직을 다형성으로 대체해야 한다는 주장에 동의하지 않는다.

* _중보고 제거를 위해 상속을 사용하는 경우가 많이 있었는데,_
* _이 역시 무분별한 상속은 유지보수를 어렵게 하므로 주의해야 할 부분이다._
* _상속을 사용할때는 리스코프 치환법칙을 생각해 보자_

## 10.5 특이 케이스 추가하기

```diff
- if (customer === "미확인 고객") customerName = "거주자"
+ class UnknownCustomer extends Customer {
+   override def name(): String = "거주자"
+ }
```

> [!NOTE]
> * 특정 값을 확인한 후 똑같은 동작을 수행하는 중복코드가 보이면
> * 특이 케이스 패턴<sup>Special Case Pattern</sup>을 적용하면 좋다.
> * 특이 케이스를 확인하는 코드 대부분을 단순한 함수 호출로 바꾸는 작업이다.

* _`객체 리터럴 이용하기` 와 `변환 함수 이용하기`는 정적타입 언어에서 실습하기 까다로와서 생략함_
* _조금 복잡해 보이기도 하면서 잘 활용하면 유용할 수 있어 보임, 다만 아직 유사한 케이스에 대한 경험이 떠오르지는 않음_

## 10.6 어서션 추가하기

```diff
+ assert(() => this.discountRate >= 0)
 if (this.discountRate) {
   base = base - (base * this.discountRate)
```

> [!NOTE]
> * 코드의 가정은 알고리즘을 분석해서 연역해야 하는 경우 보다, 주석이라도 있는 편이 낫고
> * 그 보다는 어서션<sup>assertion</sup>을 이용해서 코드 자체에 삽입하는 방식이 더 낫다.
> * 어서션의 용도는 오류 찾기 뿐만 아니라
> * **프로그램이 어떤 상태임을 가정한 채 실행되는지 알려주는 훌륭한 소통 도구이다.**
> * 단위 테스트로 사작을 좁히면 어서션의 효용은 줄어들지만
> * **소통 측면에서는 여전히 유용하다.**

_평소에 어서션을 잘 활용하는 편은 아니었으나, 좀 더 적극적으로 활용해 보아야 겠다._
_(내 기억이 맞다면) [이펙티브 소프트웨어 테스팅](https://jpub.tistory.com/1391) 에서도 어서션에 대한 내용이 있다._
_책에서는 입력 뿐만 아니라 반환 값에도 어서션 사용하는 것에 대해 설명한다._

> [!NOTE]
> 어서션을 남발하는 것 역시 위험하다.
> 모든 가정에 어서션을 달지는 않고, '반드시 참이어야 하는' 것만 검사한다.
> 이러한 조건도 중복된 코드는 반드시 제거해야 한다.(함수 추출하기)

## 10.7 제어 플래그를 탈출문으로 바꾸기

```diff
- var found = false
  for (p <- people) {
-    if (!found) {
      if (p === "조커") {
        sendAlert()
-        found = true
+        break
      }
-    }
  }
```

> * 제어 플래그란 코드의 동작을 변경하는 데 사용되는 변수를 말하며 
> * 나는 이런 코드를 항상 악취로 본다.
> * 반복문 안의 제어 플래그는 break 문이나 continue, return 문으로 명확히 알리는 편이 낫다

_지금 보면 당연해 보이지만, 한때 제어 플래그를 사용하던 시절이 생각났다_