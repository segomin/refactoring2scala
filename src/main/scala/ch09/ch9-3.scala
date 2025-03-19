package org.sangho.refac2scala
package ch09_3

case class Adjustment(amount: Int)

class ProductionPlan {
  private var _production = 0
  private var _adjustments = List[Adjustment]()

  def production: Int = {
    _adjustments.foldLeft(0)((sum, a) => sum + a.amount)
  }

  def applyAdjustment(anAdjustment: Adjustment): Unit = {
    _adjustments = anAdjustment :: _adjustments
    _production += anAdjustment.amount
  }
}

// main
@main def main(): Unit = {
  val plan = new ProductionPlan()
  plan.applyAdjustment(Adjustment(10))
  plan.applyAdjustment(Adjustment(20))
  assert(plan.production == 30)
}
