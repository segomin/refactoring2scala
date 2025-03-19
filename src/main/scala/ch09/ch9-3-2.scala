package org.sangho.refac2scala
package ch09_3_2

case class Adjustment(amount: Int)

class ProductionPlan (__production: Int) {
  private val _initialProduction = __production
  private var _adjustments = List[Adjustment]()

  def production: Int = {
    _initialProduction + calculateProductionAccumulator
  }

  def calculateProductionAccumulator: Int = {
    _adjustments.foldLeft(0)((sum, a) => sum + a.amount)
  }

  def applyAdjustment(anAdjustment: Adjustment): Unit = {
    _adjustments = anAdjustment :: _adjustments
  }
}

// main
@main def main(): Unit = {
  val plan = new ProductionPlan(10)
  plan.applyAdjustment(Adjustment(10))
  plan.applyAdjustment(Adjustment(20))
  assert(plan.production == 40)
}
