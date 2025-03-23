package org.sangho.refac2scala
package ch10_4_1

trait Bird(val kind: String) {
  def plumage: String
  def airSpeedVelocity: Int
}


class EuropeanSwallow extends Bird("유럽 제비") {
  override def plumage: String = "보통이다"
  override def airSpeedVelocity: Int = 35
}
class AfricanSwallow(numberOfCoconuts: Int) extends Bird("아프리카 제비") {
  override def plumage: String = if (numberOfCoconuts > 2) "지쳤다" else "보통이다"
  override def airSpeedVelocity: Int = 40 - 2 * numberOfCoconuts
}

class NorwegianBlueParrot(voltage: Int, isNailed: Boolean) extends Bird("노르웨이 파랑 앵무") {
  override def plumage: String = if (voltage > 100) "그을렸다" else "예쁘다"
  override def airSpeedVelocity: Int = if isNailed then 0 else 10 + voltage / 10
}

def plumages(birds: List[Bird]): Map[String, Bird] = {
  birds.map(bird => bird.kind -> bird).toMap
}

def speeds(birds: List[Bird]): Map[String, Int] = {
  birds.map(bird => bird.kind -> airSpeedVelocity(bird)).toMap
}


// 깃털 상태
def plumage(bird: Bird): String = bird.plumage

// 비행 속도
def airSpeedVelocity(bird: Bird): Int = bird.airSpeedVelocity


// main
@main def main(): Unit = {
  val europeanSwallow = new EuropeanSwallow()
  val africanSwallowNormal = new AfricanSwallow(2)
  val africanSwallowTired = new AfricanSwallow(3)
  val norwegianBlueParrot = new NorwegianBlueParrot(120, false)

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
