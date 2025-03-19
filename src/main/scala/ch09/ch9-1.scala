package org.sangho.refac2scala
package ch09_1

case class Scenario(primaryForce: Int, secondaryForce: Int, mass: Int, delay: Int)

def distanceTravelled(scenario: Scenario, time: Int): Int = {
  // 가속도(a) = 힘(F) / 질량(m)
  val primaryAcceleration = scenario.primaryForce / scenario.mass
  val primaryTime = Math.min(time, scenario.delay)
  // 전파된 거리
  val primaryDistance: Int = (0.5 * primaryAcceleration * primaryTime * primaryTime).toInt
  val secondaryTime = time - scenario.delay
  if (secondaryTime <= 0) {
    return primaryDistance
  }

  // 두 번째 힘을 반영해 다시 계산
  val primaryVelocity = primaryAcceleration * scenario.delay
  val secondaryAcceleration = (scenario.primaryForce + scenario.secondaryForce) / scenario.mass
  val secondaryDistance = primaryVelocity * secondaryTime + (0.5 * secondaryAcceleration * secondaryTime * secondaryTime).toInt
  primaryDistance + secondaryDistance
}


// main
@main def main(): Unit = {
  val scenario = Scenario(30, 20, 10, 40)
  assert(distanceTravelled(scenario, 35) == 1837)
  assert(distanceTravelled(scenario, 100) == 18600)
}
