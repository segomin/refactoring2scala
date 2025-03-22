package org.sangho.refac2scala
package ch10_4_1

class Bird(val kind: String, val numberOfCoconuts: Int, val voltage: Int, val isNailed: Boolean = false)

def plumages(birds: List[Bird]): Map[String, Bird] = {
  birds.map(bird => bird.kind -> bird).toMap
}

def speeds(birds: List[Bird]): Map[String, Int] = {
  birds.map(bird => bird.kind -> airSpeedVelocity(bird)).toMap
}


// 깃털 상태
def plumage(bird: Bird): String = {
  bird.kind match {
    case "유럽 제비" => "보통이다"
    case "아프리카 제비" => if (bird.numberOfCoconuts > 2) "지쳤다" else "보통이다"
    case "노르웨이 파랑 앵무" => if (bird.voltage > 100) "그을렸다" else "예쁘다"
    case _ => "알 수 없다"
  }
}

// 비행 속도
def airSpeedVelocity(bird: Bird): Int = {
  bird.kind match {
    case "유럽 제비" => 35
    case "아프리카 제비" => 40 - 2 * bird.numberOfCoconuts
    case "노르웨이 파랑 앵무" => if bird.isNailed then 0 else 10 + bird.voltage / 10
    case _ => 0
  }
}


// main
@main def main(): Unit = {
  val europeanSwallow = new Bird("유럽 제비", 0, 0)
  val africanSwallowNormal = new Bird("아프리카 제비", 2, 0)
  val africanSwallowTired = new Bird("아프리카 제비", 3, 0)
  val norwegianBlueParrot = new Bird("노르웨이 파랑 앵무", 0, 120, false)

  assert(plumage(europeanSwallow) == "보통이다")
  assert(plumage(africanSwallowNormal) == "보통이다")
  assert(plumage(africanSwallowTired) == "지쳤다")
  assert(plumage(norwegianBlueParrot) == "그을렸다")

  assert(airSpeedVelocity(europeanSwallow) == 35)
  assert(airSpeedVelocity(africanSwallowNormal) == 36)
  assert(airSpeedVelocity(norwegianBlueParrot) == 22)

  val birds = List(europeanSwallow, africanSwallowNormal, norwegianBlueParrot)
  assert(plumages(birds) == Map(
    "유럽 제비" -> europeanSwallow,
    "아프리카 제비" -> africanSwallowNormal,
    "노르웨이 파랑 앵무" -> norwegianBlueParrot))
  assert(speeds(birds) == Map("유럽 제비" -> 35, "아프리카 제비" -> 36, "노르웨이 파랑 앵무" -> 22))
}
