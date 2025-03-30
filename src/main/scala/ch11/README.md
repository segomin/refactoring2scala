- [11.1 질의 함수와 변경 함수 분리하기 (Separate Query from Modifier)](#111-질의-함수와-변경-함수-분리하기-separate-query-from-modifier)
- [11.2 함수 매개변수화하기 (Parameterize Function)](#112-함수-매개변수화하기-parameterize-function)
- [11.3 플래그 인수 제거하기 (Remove Flag Argument)](#113-플래그-인수-제거하기-remove-flag-argument)
- [11.4 객체 통째로 넘기기 (Preserve Whole Object)](#114-객체-통째로-넘기기-preserve-whole-object)
- [11.5 매개변수를 질의 함수로 바꾸기 (Replace Parameter with Query)](#115-매개변수를-질의-함수로-바꾸기-replace-parameter-with-query)
- [11.6 질의 함수를 매개변수로 바꾸기 (Replace Query with Parameter)](#116-질의-함수를-매개변수로-바꾸기-replace-query-with-parameter)
- [11.7 세터 제거하기 (Remove Setting Method)](#117-세터-제거하기-remove-setting-method)
- [11.8 생성자를 팩터리 함수로 바꾸기 (Replace Constructor with Factory Function)](#118-생성자를-팩터리-함수로-바꾸기-replace-constructor-with-factory-function)
- [11.9 함수를 명령으로 바꾸기 (Replace Function with Command)](#119-함수를-명령으로-바꾸기-replace-function-with-command)
- [11.10 명령을 함수로 바꾸기 (Replace Command with Function)](#1110-명령을-함수로-바꾸기-replace-command-with-function)
- [11.11 수정된 값 반환하기 (Return Modified Value)](#1111-수정된-값-반환하기-return-modified-value)
- [11.12 오류 코드를 예외로 바꾸기 (Replace Error Code with Exception)](#1112-오류-코드를-예외로-바꾸기-replace-error-code-with-exception)
- [11.13 예외를 사전확인으로 바꾸기 (Replace Exception with Precheck)](#1113-예외를-사전확인으로-바꾸기-replace-exception-with-precheck)

# CHAPTER 11 API 리팩터링

> * 모듈과 함수는 소프트웨어를 구성하는 빌딩 블록이며, API는 이 블록을 끼워 맞추는 연결부다.
> * 이런 API를 이해하기 쉽고 사용하기 쉽게 ㅁ나드는 일은 중요한 동시에 어렵기도 하다.
> * 그래서 API를 개선하는 방법을 새로 깨달을 때마다 그에 맞게 리팩터링해야 한다.

여기서의 API는 함수의 인터페이스를 의미한다. 

> [!TIP]
> * 두 기능이 섞여 있다면 `질의 함수와 변경 함수 분리하기`
> * 값 하나 때문에 나뉜 함수들은 `함수 매개변수화하기`
> * 함수의 동작 모드 전환용 매개변수는 `플래그 인수 제거하기`
> * 데이터 구조가 필요이상 분해될 경우 `객체 통째로 넘기기`
> * 무언가를 매개변수로 건네 함수 내에서 판단할지 아니면 호출 함수가 직접 정할지에 따라
>   * `매개변수를 질의 함수로 바꾸기`
>   * `질의 함수를 매개변수로 바꾸기`
> * 클래스가 불변이길 원할때는 `세터 제거하기`
> * 새로운 객체를 만들어 반환할때 `생성자를 팩터리 함수로 바꾸기`
> * 수많은 데이터를 받는 복잡한 함수를 잘게 쪼개는 문제는
>   * 객체로 변환하여 본문에서 함수를 추출할 때 `함수를 명령으로 바꾸기`
>   * 함수를 단순화하여 명령 객체가 필요 없어지면 `명령을 함수로 바꾸기`

## 11.1 질의 함수와 변경 함수 분리하기 (Separate Query from Modifier)

```diff
- def getTotalOutstandingAndSendBill(): Int = {
-   val result = customer.invoics.foldLeft(0)((total, each) => total + each.amount)
-   sendBill();
-   result
- }
+ def totalOutstanding(): Int = {
+   customer.invoics.foldLeft(0)((total, each) => total + each.amount)
+ }
+ def sendBill(): Unit = {
+   ???
+ }
```

> [!NOTE]
> * 외부에서 관찰할 수 있는 겉보기 부수효과가 전혀 없이 값을 반환해주는 함수를 추구해야 한다. (순수함수)
> * 명령-질의 분리<sup>command-query separation</sup> : 부수효과가 없는 질의 함수(읽기 함수)를 분리
> * 절대적으로 신봉하지는 않더라도 되도록 따를 경우 좋은 효과를 얻을 수 있다.

## 11.2 함수 매개변수화하기 (Parameterize Function)
```diff
- def tenPercentRaise(person: Person): Unit = {
-   person.salary = person.salary.multiply(1.1)
- }
- def fivePercentRaise(person: Person): Unit = {
-   person.salary = person.salary.multiply(1.05)
- }
+ def raise(person: Person, factor: Double): Unit = {
+   person.salary = person.salary.multiply(factor)
+ }
```


> [!NOTE] 
> * 두 함수의 로직이 아주 비슷하고 단지 리터럴 값만 다르다면, 
> * 다른 값만 매개변수로 받아 처리하는 하나의 함수로 중복을 없앨 수 있다. 

_딱히 인지히지 않고 있어도 자연스레 이용하고 있었던 기법이다._

## 11.3 플래그 인수 제거하기 (Remove Flag Argument)
```diff
- def setDimension(name: String, value: Int): Unit = {
-   if (name == "width") {
-     _width = value
-     return
-   }
-   if (name == "height") {
-     _height = value
-     return
-   }
- }
+ def setHeight(value: Int): Unit = {
+   _height = value
+ }
+ def setWidth(value: Int): Unit = {
+   _width = value
+ }
```

> [!NOTE]
> * 플래그 인수는 호출할 수 있는 함수들이 무엇이고 어떻게 호출해야 하는지를 이해하기 어려워 진다(js 경우 특히)
> * 함수 목록을 살펴볼 때 플래그 인수가 있으면 함수들의 기능 차이가 잘 드러나지 않는다
> * boolean 플래그는 특히 코드의 의미를 온전히 전달하지 못한다

* 매개변수에 의한 조건문을 함수로 추출하거나, 까다로운 경우 래핑 함수로 감싼다

## 11.4 객체 통째로 넘기기 (Preserve Whole Object)
```diff
- val low = room.daysTempRange.low
- val high = room.daysTempRange.high
- if (!plan.withinRange(low, high))
+ if (!plan.withinRange(room.daysTempRange))
```

> [!NOTE]
> * 객체를 통째로 넘길때의 장점
>  1. 변화에 대응하기 쉽다
>  2. 매개변수 목록이 짧아져서 함수 사용법을 이해하기 쉬워진다
>  3. 사용하는 함수가 여럿일 때 중복 로직을 없앨 수 있다
> * 사용하지 않는 경우
>  1. 레코드와 함수가 서로 다른 모듈에 있을 때
>  2. 레코드의 부분만 사용하는 경우

* 절차
  * 기존함수를 감싸는 함수를 만들고
  * 감싸는 함수에서 매개변수를 객체를 받고록 함
  * 감싸는 함수에서 객체의 속성을 사용하여 기존 함수를 호출함

## 11.5 매개변수를 질의 함수로 바꾸기 (Replace Parameter with Query)

```diff
- availableVacation(employee, employee.grade)
+ availableVacation(employee)
- def availableVacation(employee: Employee, grade: Int): Int = {
+ def availableVacation(employee: Employee): Int = {
+   val gradle = employee.grade
```

> [!NOTE]
> * 매개변수 목록은 함수의 변동 요인을 모아놓은 곳이다.
> * 피호출 함수가 스스로 '쉽게' 결정할 수 있는 값을 매개변수로 건네는 것도 일종의 중복이다.
> * 가능한한 피호출 함수가 필요한 매개변수를 결정하도록 하여 호출하는 쪽을 간소하게 만든다  
> * 주의사항은 대상 함수가 참조 투명<sup>referential transparency</sup>해야 한다
> * 참조 투명이란 '함수에 똑같은 값을 건네 호출하면 항상 똑간이 동작한다'는 뜻이다.

## 11.6 질의 함수를 매개변수로 바꾸기 (Replace Query with Parameter)

```diff
- targetTemperature(plan)
+ targetTemperature(plan, thermostat.currentTemperature)
- def targetTemperature(plan: Plan): Int = {
-  val currentTemporature = thermostat.currentTemperature
+ def targetTemperature(plan: Plan, currentTemporature: Int): Int = {
  ...
```

> [!NOTE]
> * 함수안에서 전역 변수를 참조한다거나 제거하길 원하는 원소를 참조하는 경우 참조를 매개변수로 바꿔 해결할 수 있다.
> * 매개변수로 바꾸는 경우와 함수끼리 많은 것을 공유하여 결합을 만드는 사이에 적절한 균형을 찾아야 한다.
> * 이 리펙토링의 단점은 호출자의 책임이 늘어나고, 복잡해 진다.

> [!TIP]
> * 참조 투명하지 않은 원소를 매개변수로 바꾸면 해당 함수는 참조 투명해 진다.
> * 그래서 모듈을 개발할 때 순수 함수들을 따로 구분하고,
> * 프로그램의 입출력과 기타 가변 원소들을 다루는 로직은 수순 함수들의 겉을 감싸는 패턴을 많이 활용한다.

* 의존성을 모듈 바깥으로 밀어내는 리펙토링은 책임을 호출자에게 지운다.
* 이를 통해서 객체와의 결합만을 제거하는 것이 아니라, 
* 가변 데이터를 추출함으로서 클래스나 함수를 불변으로 만들어서 참조투명성을 얻을 수 있다.

## 11.7 세터 제거하기 (Remove Setting Method)

```diff
class Person {
  private var _name: String = ""
  def name: String = _name
-  def name_=(value: String): Unit = { _name = value }
}
```

> [!NOTE]
> * 세터를 제거하면 해당 필드를 불변으로 만든다는 의도가 명확해 진다.

## 11.8 생성자를 팩터리 함수로 바꾸기 (Replace Constructor with Factory Function)
```diff
- val leadEngineer = new Employee(document.leaEnineer, "E")
+ val leadEngineer = createEngineer(document.leadEngineer)
```

> [!NOTE]
> * 생성자에는 반드시 그 생성자를 정의한 클래스의 인스턴스를 반환해야 하거나
> * 생성자의 이름을 임의로 정할 수 없다. (더 적절한 이름이 있더라도)
> * 팩터리 함수에는 이런 제약이 없다.

## 11.9 함수를 명령으로 바꾸기 (Replace Function with Command)
```diff
- def score(candidate: Candidate, medicalExam: MedicalExam, scoringGuide: ScoringGuide): Int = {
     var result = 0
     var healthLevel = 0
     ???
+ class Scoring (val candidate: Candidate, val medicalExam: MedicalExam, val scoringGuide: ScoringGuide) {
+   var result = 0
+   var healthLevel = 0
+   def execute(): Int = {
+     ???
 }
```

> [!NOTE]
> * 함수를 그 함수만을 위한 객체 안으로 캡슐화하면 더 유용해지는 상황이 있다.
> * 이런 객체를 명령 객체<sup>command object</sup> 혹은 명령<sup>command</sup>이라고 한다.
> * 명령은 되돌리기<sup>undo</sup> 같은 보조 연산을 제공하거나,
> * 기타 다양한 사용자 맞춤형 기능을 제공할 수 있다.
> * 하지만 **유연성은 복잡성을 키우고 얻는 대가임을 잊지 말아야 한다.**

> [!NOTE]
> * `명령` 이라는 용어는 맥락에 따라서 다른 의미를 가질 수 있다.
> * `명령 객체` 에서의 명령은 디자인 패턴의 `명령 패턴`에서 말하는 명령과 같고
> * `명령-질의 분리 원칙` 에서의 명령은 객체의 상태를 변경하는 메서드를 가리킨다.

_리펙토링 과정에서 지역변수를 필드로 추출하는 과정이 있는데,_ 
_언뜻 보면 가독성이 떨어지는 것처럼 보이지만 해당 클래스가 기존 함수의 역할만 한다면 충분히 응집도가 높은 형태로 된다고 본다._
_해당 클래스 확장시 응집도를 해치지 않도록 조심해야 한다._

## 11.10 명령을 함수로 바꾸기 (Replace Command with Function)
```diff
- class ChargeCalculator(customer: Customer, usage: Int) {
-   def execute(): Int = customer.rate * usage
- }
+ def charge(customer: Customer, usage: Int): Int = customer.rate * usage
```

* [!NOTE]
* 명령 객체의 장점보다 단점이 크다면(복잡성을 상쇄할 장점이 없다면) 평범한 함수로 바꿔주는 게 낫다

* _여전히 작은 크기로 단계별 리펙토링을 진행하는 것을 보여주고 있다_

## 11.11 수정된 값 반환하기 (Return Modified Value)

```diff
- var totalAscent = 0
- calculateAscent()
+ val totalAscent = calculateAscent()
- def calculateAscent(): Unit = {
+ def calculateAscent(): Int = {
+   var result = 0
   for (i <- 1 until points.size) {
     val verticalChange = points(i).elevation - points(i-1).elevation
-     totalAscent += if (verticalChange > 0) verticalChange else 0
+     result += if (verticalChange > 0) verticalChange else 0
   }
+   result
 }
```

> [!NOTE]
> * 데이터를 수정하는 코드가 여러 곳이라면 데이터가 수정되는 흐름과 코드의 흐름을 일치시키기 어렵다.
> * 그래서 데이터가 수정되면 그 사실을 명확히 알려주는 일은 중요하다.
> * 값 하나를 계산하는 목적의 함수에서는 수정된 값을 반환하여 직접 변수에 담도록 하는 리펙토링이 효과적이다.

## 11.12 오류 코드를 예외로 바꾸기 (Replace Error Code with Exception)
```diff
if (data) {
  new ShippingRules(data)
} else {
-  -23
+  throw new OrderProcessingError(-23)
}
```

> [!NOTE]
> 예외는 프로그래밍 언어에서 제공하는 독립적인 오류 처리 메커니즘이다.
> 예외를 사용하면 오류코드를 일일이 검사하거나 오류를 식별해 콜스택 위로 던지는 일을 하지 않아도 된다.
> 단, **예외는 정확히 예상 밖의 동작일 때만 쓰여야 한다.**

> [!IMPORTANT]
> * 예외를 던지는 코드를 프로그램 종료 코드로 바꿔도 프로그램이 여전히 정상 동작할지를 따져본다.
> * 정상동작 하지 않을 것 같다면 예외를 사용하지 말라는 신호다.
> * _의미를 잘 이해하지 못한 부분이다_

* _오류를 처리하는 기준을 잡는게 쉽지 않다고 생각한다. 오류를 처리하는 코드 자체가 가독성을 떨어뜨리기도 하고,_
* _잘못된 기준으로 처리한 오류는 디버깅을 어렵게 만들기도 한다._
* _여전히 golang 에서는 Err 형태로 오류를 처리하고 있고, 함수형에서는 Monad 을 통해서 오류를 처리하는 방법을 사용하고 있다._
* _일반적인 객체지향에서는 에러를 던지는게 맞을지 모르지만, 언어별로 오류를 처리하는 방법을 따라야 한다고 생각한다._
* _코틀린은 자바와 달리 checked exception 이 없다는 것도 언어별 차이가 있다._

## 11.13 예외를 사전확인으로 바꾸기 (Replace Exception with Precheck)
```diff
 def getValueForPeriod(periodNumber: Int): Double = {
-   try {
-     values(periodNumber)
-   } catch {
-     case e: ArrayIndexOutOfBoundsException => 0.0
-   }
+   if (periodNumber < 0 || periodNumber >= values.length) 0.0 else values(periodNumber)
 }
```

> [!NOTE]
> * 예외는 '뜻밖의 오류'라는 의미로 예외적으로 동작할 때만 사용해야 한다.
> * 함수 수행시 문제가 될 수 있는 조건을 검사할 수 있다면 예외를 던지는 대신 호출하는 곳에서 조건을 검사하도록 한다.

