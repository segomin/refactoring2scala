# CHAPTER 04 테스트 구축하기

> 리펙터링을 제대로 하려면 불가피하게 저지르는 실수를 잡아주는 견고한 테스트 스위트가 뒷받침돼야 한다

리펙터링이 아니더라도 테스트 작성은 개발의 효율을 높여준다.

## 4.1 자가 테스트 코드의 가치

프로그래머들은 실제로 코드를 작성하는 시간보다 분석, 설계 그리고 **디버깅**에 대부분의 시간을 쓴다.

> 모든 테스트를 완전히 자동화하고 그 결과까지 스스로 검사하게 만들자.

이렇게 하면 테스트가 컴파일 만큼 쉬워지고, 생산성이 높아진다.

> 테스트를 작성하기 가장 좋은 시점은 프로그래밍을 시작하기 전이다.

TDD 를 사용하면 구현보다 인터페이스에 집중하고 완료시점을 판단할 수 있다.

레드-그린-리팩터링 사이클을 한 시간에도 여러 차례 진행하면 생산적이면서도 차분하게 작업할 수 있다.

## 4.2 테스트할 샘플 코드

Scala 에서는 함수를 Value 처럼 사용할 수 있어서 Javascript 의 get 함수 예제와 유사하게 작성할 수 있다.

```scala
class Province {
...

  def profit: Int = demandValue - demandCost

  def demandValue: Int = satisfiedDemand * price

  def satisfiedDemand: Int = Math.min(demand, totalProduction)

  def demandCost: Int = {
  ...
  }
}
```

## 4.3 첫 번째 테스트

> describe 블록과 it 블록에 부연 설명용 문자열을 써넣는 방식은 개발자마다 다르다.
>
> 나는 실패한 테스트가 무엇인지 실벽할 수 있을 정도로만 작성하는 편이다.
>
> 실패해야 할 상황에서는 반드시 실패하게 만들자.

* Tip. 일시적으로 코드에 오류를 주입하여 실패한 테스트를 확인한 후 오류를 제거하여 정상 동작을 확인함
* 처음부터 성공하는 테스트를 만들때도 있었는데, 이런경우 오류 코드를 넣어서 재확인 하는 방법은 종종 사용해 보곤 했다.

> 핵심은 (GUI냐 콘솔이냐가 아니라) 모든 테스트가 통과했다는 사실을 빨리 알 수 있다는 데 있다.

## 4.4 테스트 추가하기

> 테스트는 위험요인을 중심으로 작성해야 한다!
> 테스트를 너무 많이 만들다 보면 옹히려 필요한 테스트를 놓치기 쉽기 때문에 아주 중요한 포인트다. 나는 적은 수의 테스트만으로 큰 효과를 얻고 있다.
>
> 완벽하게 만드느라 테스트를 수행하지 못하느니, 불완전한 테스트라도 작성해 실행하는게 낫다.

회사에서 테스트 도입이 더디다 보니, 경계를 포함한 꼼꼼한 테스트 작성 보다는 비즈니스 로직을 분리하고 로직에 대한 일반적인 테스트만 추가하는 것에 만족하고 있다.
SUT(System under test) 에서 버그가 발견될 때 테스트를 추가하는 방식으로 진행하고 있다.
이 방식이 이상적이지는 않지만, 우선은 테스트 문화 도입을 목표로 한 타협점이라고 생각한다.

* Tip.
    1. 기대값 자리에 임의의 값을 넣고 테스트 수행 (Red)
    2. 프로그램이 내놓는 실제 값으로 대체 (Green)
    3. 테스트가 작동 확인
    4. 임의로 오류를 주입하여 테스트 실패 확인 (Red)
    5. 오류를 걸러내는 테스트 작동 확인
    6. 만족해 하면 오류코드 원복 (Green)

> 테스트들이 모두 똑같은 픽스처에 기초하여 검증을 수행해야
> 표준픽스처에 익숙해져서 테스트할 속성을 다양하게 찾아낼 수 있다

## 4.5 픽스처 수정하기

> 설정-실행-검증 (setup-exercise-verify)
>
> 조건-발생-결과 (given-when-then)
>
> 준비-수행-단언 (arrange-act-assert)

3가지 다 같은 말이지만, 일달 given when then 이 제일 익숙하다.

> 일반적으로 it 구문 하나당 검증도 하나씩만 하는 게 좋다.

이 부분이 생각보다 쉽지는 않은듯 하다.
우선은 테스트 하나에 하나만 검증한다는 이상을 가지고
너무 중복이 많다고 생각되면 적당한 선에서 적용하고 있다.

## 4.6 경계 조건 검사하기

> 경계 지점에서 문제가 생기면 어떤 일이 벌어지는지 확인하는 테스트도 함께 작성하면 좋다.

> 경계를 확인하는 테스트를 작성해보면 프로그램에서 이런 특이 상황을 어떻게 처리하는 게 좋을지 생각해 볼 수 있다.

> 어차피 모든 버그를 잡아낼 수는 없다고 생각하여 테스트를 작성하지 않는다면 대다수의 버그를 잡을 수 있는 기회를 날리는 셈이다.

> `테스트에도 수확 체감 법칙`이 적용된다

경계 테스트를 생각하다 보면 테스트가 많아질 수 있다. 아직은 경계 테스트에 대해 깊이 생각하지는 않고 있다. 

경계 테스트 관련해서는 [이펙티브 소프트웨어 테스팅](https://product.kyobobook.co.kr/detail/S000201055864) 에 잘 나와있다.

코드에서 처리과정이 복잡하거나, 위험한 부분에 집중해서 테스트를 작성하는 것이 좋다.

## 4.7 끝나지 않은 여정

> 테스트는 리펙터링에 반드시 필요한 토디일 뿐만 아니라, 그 자체로도 프로그래밍에 중요한 역할을 한다.
> 
> 테스트 용이성을 아키텍처 평가 기준으로 활용하는 사례도 많다.

> 기존 테스트가 충분히 명확한지, 테스트 과정을 더 이해하기 쉽게 리팩터링할 수는 없는지, 제대로 검사하는지 등을 확인하자. 
> 
> 버그 리포트를 받으면 가장 먼저 그 버그를 드러내는 단위 테스트부터 작성하자.

버그 수정시 급한 마음에 테스트 코드를 작성하지 않는 경우가 많다. 주변에 테스트 코드가 없는 경우는 더더욱 그렇다. 
평소에 꽃길(happy path) 테스트라도 만들어 두면 이런 경우 높은 확율로 버그에 해당하는 테스트 코드를 작성하게 된다.

비록 테스트 커버리지가 품질과 크게 상관이 없다고는 하나, 테스트를 작성하는데 큰 동기가 되는것은 맞다.
