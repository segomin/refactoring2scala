- [12.1 메서드 올리기](#121-메서드-올리기-pull-up-method)
- [12.2 필드 올리기](#122-필드-올리기-pull-up-field)
- [12.3 생성자 본문 올리기](#123-생성자-본문-올리기-pull-up-constructor-body)
- [12.4 메서드 내리기](#124-메서드-내리기-push-down-method)
- [12.5 필드 내리기](#125-필드-내리기-push-down-field)
- [12.6 타입 코드를 서브클래스로 바꾸기](#126-타입-코드를-서브클래스로-바꾸기-replace-type-code-with-subclass)
- [12.7 서브클래스 제거하기](#127-서브클래스-제거하기-remove-subclass)
- [12.8 슈퍼클래스 추출하기](#128-슈퍼클래스-추출하기-extract-superclass)
- [12.9 계층 합치기](#129-계층-합치기-collapse-hierarchy)
- [12.10 서브클래스를 위임으로 바꾸기](#1210-서브클래스를-위임으로-바꾸기-replace-subclass-with-delegate)
- [12.11 슈퍼클래스를 위임으로 바꾸기](#1211-슈퍼클래스를-위임으로-바꾸기-replace-superclass-with-delegate)

# CHAPTER 12 상속 다루기

* 상속은 아주 유용한 동시에 오용하기 쉽다.
* 이 장에서도 리펙터링의 양면성을 볼 수 있었다.

## 12.1 메서드 올리기 (Pull Up Method)

```diff
sealed trait Employee {
+  def name: String
}
case class Salesperson(name: String) extends Employee
case class Engineer(name: String) extends Employee
```

> [!Note]
> * 중복 코드 제거는 중요하다. 

_필드에 관한 메서드인 경우 필드를 올리는 것과 유사함_

## 12.2 필드 올리기 (Pull Up Field)

```diff
- abstract class Employee {}
+ abstract class Employee(val name: String) {}
- class Salesperson(val name: String) extends Employee
- class Engineer(val name: String) extends Employee
+ class Salesperson(name: String) extends Employee(name)
+ class Engineer(name: String) extends Employee(name)
```
> [!Note]
> * 필드들이 비슷한 방식으로 쓰인다고 판단되면 슈퍼클래스로 끌어올리자.
> * 이렇게 하면 데이터 중복 선언과, 필드를 사용하는 동작을 줄일 수 있다.

## 12.3 생성자 본문 올리기 (Pull Up Constructor Body)

```diff
- abstract class Party {}
+ abstract class Party(val name: String) {}
- class Employee(val name: String, val id: String, val monthlyCost: Int) extends Party {}
+ class Employee(name: String, val id: String, val monthlyCost: Int) extends Party(name) {}
```

> [!Note]
> * 생성자는 일반 메서드와 달리 제약을 두는 편임
> * 생성자는 할 수 있는 일과 호출 순서에 제약이 있기 때문에 일반 메서드와는 다른 식으로 접근해야 한다.

* _스칼라에서는 필드를 올리는 것과 생성자를 올리는것 두개의 차이가 거의 없다._
* _기본 생성자에 로직을 넣기 번거롭게 돠어 있다._

## 12.4 메서드 내리기 (Push Down Method)

```diff
class Employee {
-  def quota(): Int = {...}
}

class Enginner extends Employee {...}
class Salesperson extends Employee {
+  def quota(): Int = {...}
}
```

_영문으로 왜 pull down 이 대신 push down 을 사용했을까?_

## 12.5 필드 내리기 (Push Down Field)

```diff
- class Employee(val quota: Int) {...}
+ class Employee() {...}

- class Enginner(quota: Int) extends Employee(quota) {...}
+ class Enginner() extends Employee {...}
- class Salesperson(quota: Int) extends Employee(quota) {...}
+ class Salesperson(val quota: Int) extends Employee {...}
```

## 12.6 타입 코드를 서브클래스로 바꾸기 (Replace Type Code with Subclass)

```diff
def createEmployee(name: String, kind: String): Employee = {
-  new Employee(name, kind)
+  kind match {
+    case "engineer" => new Employee(name)
+    case "salesperson" => new Salesperson(name)
+    case "manager" => new Manager(name)
+  }
}
```

> [!Note]
> * 서브클래스는 조건에 따라 다르게 동작하도록 해주는 다형성을 제공한다.
> * 서브클래스는 특정 타입에서만 의미있는 필드나 메서드를 갖도록 할 수 있다.
> * 리펙터링은 대상 클래스에 직접 적용하거나, 타입 코드 자체에 적용 할 수 있다

_이 단계별로 리펙터링을 진행하는 과정을 잘 활용하면 복잡한 코드에서 
안정적인 리펙토링이 가능해 보인다._

_정적타입 언어에서는 toString 보다는 값을 override 하는게 더 나아 보였고, 
일반적으로는 enum 을 사용할듯 함_


## 12.7 서브클래스 제거하기 (Remove Subclass)

```diff
- class Person {
-   def genderCode = "X"
- }
+ class Person(val genderCode: String)
- class Male extends Person {
-   override def genderCode = "M"
- }
- class Female extends Person {
-   override def genderCode = "F"
- }
```

> [!Note]
> * 더 이상 쓰이지 않는 서브클래스는 슈퍼클래스의 필드로 대체해 제거한다.

## 12.8 슈퍼클래스 추출하기 (Extract Superclass)

```diff
+ class Party(val name: String) {
+   def annualCost: Int = {...}
+ }
- class Department(val name: String) {
+ class Department(name: String) extends Party(name) {
-   def totalAnnualCost: Int = {...}
+   override def annualCost: Int = {...}
  def headCount: Int = {...}
}
- class Employee(val name: String) {
+ class Employee(name: String) extends Party(name) {
-   def annualCost: Int = {...}
+   override def annualCost: Int = {...}
  def id: String = {...}
}
```

> [!Note]
> * 비슷한 일을 수행하는 두 클래스의 비슷한 부분을 공통의 슈퍼클래스로 옮길 수 있다.
> * 상속은 프로그램이 성장하면서 깨우쳐가게 되며, 
> * 슈퍼클래스로 끌어올리고 싶은 공통 요소를 찾았을 때 수행하는 사례가 잦다.

* _중복제거를 위해 상속을 생각하기는 쉽지만,_ 
* 결합이 강해지는 이유로 상속보다는 합성<sup>[Composition over inheritance](https://en.wikipedia.org/wiki/Composition_over_inheritance)</sup>을 사용하는게 좋다고 배웠다._

## 12.9 계층 합치기 (Collapse Hierarchy)

```diff
class Employee {...}
- class Salesperson extends Employee {...}
```

> [!Note]
> * 어떤 클래스와 그 부모가 너무 비슷해져서 더는 독립적으로 존재할 이유가 사라지면 하나로 합쳐야 할 시점이다.

## 12.10 서브클래스를 위임으로 바꾸기 (Replace Subclass with Delegate)

```diff
- class Person {
```

> [!Note]
> * 공통 데이터와 동작을 슈퍼클래스에 두는 형태는 구현하기 쉽기 때문에 흔히 활용된는 메커니즘이다. 
> * 다만 단점이 있다.
> 1. 상속은 한 번만 쓸 수 있다.
> 2. 상속을 클래스들의 관계를 아주 긴밀하게 결합한다.
> * 위임은 이 두가지 문제를 해결해 준다.
> * 상속보다는 합성<sup>[Composition over inheritance](https://en.wikipedia.org/wiki/Composition_over_inheritance)</sup>을 사용하라는 원칙이 있다.
> * 처음에는 상속으로 접근한 다음, 문제가 생기기 시작하면 위임으로 갈아타면 된다.

> * 슈퍼클래스를 수정할 때 서브클래스의 동작이 망가지거나,
> * 현재의 사브클래스의 가치보다 더 큰 다른 상속의 가치가 필요한 경우 서브클래스는 위임으로 바꾸면 된다.

* _저자의 말대로, 상속을 무조건 나쁘게 생각해서 피하는게 아니라,_ 
* _상속을 위임으로 바꿀 수 있는 유연한 사고를 가지고 있는게 더 중요한듯_

> [!Note]
> * 절차
> * 생성자를 팩터리 함수로 바꾼다
> * 위임 클래스를 새로 만든다 (서브클래스가 사용하던 매개변수와 역참조를 매개변수로 받는다)

* _순서 8 을 7 보다 먼저 해야 테스트가 통과함_
* _hasDinner 를 슈퍼클래스로 올리는 부분은 js 의 undefined 보다는 false 가 낫다고 생각함_
* _저자의 경우 에러를 던진다고 하지만, 상황에 따라 false 가 더 적합할 수 있어 보인다._

> [!Note]
> * 서브클래스를 위임으로 바꾸는 리펙터링 만으로는 코드 개선이 느껴지지 않을 수 있다.
> * 위임을 적용하면 분배 로직과 양방향 참조가 생기며 복잡도가 높아지기 때문이다.
> * 동적으로 위임을 사용하거나, 상송을 다른 목적으로 사용할 수 있는 장점이 있을 수 있다.

* _여러 다양한 서브클래스를 절차를 지키면서 리펙토링을 해보면서 작은 단계별 절차의 중요성을 느끼게됨_ 
* _Norway Delegate 의 경우도 default plumage 를 생각했으나, 특정 delegate 만 사용 할 수 있는 좋은 예시였다._
* _스칼라의 패턴매칭 사용시 instanceof 보다 간소한 코드가 된다_

> [!Note]
> * "(클래스) 상속보다 (객체) 컴포지션을 사용하라" 보다는 
> * "컴포지션이나 상속 어느 하나만 고집하지 말고 적절히 혼용하라" 가 더 좋다고 본다

## 12.11 슈퍼클래스를 위임으로 바꾸기 (Replace Superclass with Delegate)

```diff
class List {...}
- class Stak extends List {...}
+ class Stack(private val _storage: List) {...}
```

> [!Note]
> * 상속이 혼란과 복잡도를 키우는 방식으로 이뤄지기도 한다
> * 잘못 적용된 예로 Java 에서 리스트를 스택이 상속받는 케이스가 있다
> * 스택에는 사용하지 않는 인터페이스들이 노출되는 문제가 있다.
> * 제대로된 상속은 서브클래스가 슈퍼클래스의 모든 기능을 사용함은 물론,
> * 서브클래스의 인스턴스를 슈퍼클래스의 인스턴스로 취급할 수 있어야 한다.
> * 위임의 단점은 위임의 기능을 이용할 호스트의 함수 모두를 전달 함수로 만들어야 한다는 점이다.
> * 전달 함수를 작성하기란 지루한 일이나, 문제가 생길 가능성은 적다.
> * 상속을 먼저 적용하고, 문제가 생기면 슈퍼클래스를 위임으로 바꾸라고 조언한다.

* _여기서의 한가지 기준은 리스코프 치환 법칙이 유용해 보인다._
* _Effective Java 에서도 리스트의 잘못된 상속에 대한 내용을 다루고 있다._
* _더 다듬기에서 나라면 Scroll 에 catalog 대신 catalogItem 를 주입했을것 같다_
