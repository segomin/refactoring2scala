package org.sangho.refac2scala
package ch09_3_2

case class Adjustment(amount: Int)

class ProductionPlan (__production: Int) {
  private var _production = __production
  private var _adjustments = List[Adjustment]()

  def production: Int = _production

  def applyAdjustment(anAdjustment: Adjustment): Unit = {
    _adjustments = anAdjustment :: _adjustments
    _production += anAdjustment.amount
  }
}

// main
@main def main(): Unit = {
  val plan = new ProductionPlan(0)
  plan.applyAdjustment(Adjustment(10))
  plan.applyAdjustment(Adjustment(20))
  assert(plan.production == 30)
}
