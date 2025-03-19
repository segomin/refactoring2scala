package org.sangho.refac2scala
package ch09_1

case class Scenario(primaryForce: Int, secondaryForce: Int, mass: Int, delay: Int)

def distanceTravelled(scenario: Scenario, time: Int): Int = {
  var result: Int = 0
  // 가속도(a) = 힘(F) / 질량(m)
  var acc = scenario.primaryForce / scenario.mass
  val primaryTime = Math.min(time, scenario.delay)
  // 전파된 거리
  result = (0.5 * acc * primaryTime * primaryTime).toInt
  val secondaryTime = time - scenario.delay
  if (secondaryTime > 0) {
    // 두 번째 힘을 반영해 다시 계산
    val primaryVelocity = acc * scenario.delay
    acc = (scenario.primaryForce + scenario.secondaryForce) / scenario.mass
    result += primaryVelocity * secondaryTime + (0.5 * acc * secondaryTime * secondaryTime).toInt
  }
  result
}


// main
@main def main(): Unit = {
  val scenario = Scenario(10, 20, 30, 40)
  val actual = distanceTravelled(scenario, 100)
  println(actual)
  assert(actual == 1800)
}
