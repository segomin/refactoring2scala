package org.sangho.refac2scala
package ch09_3_2

case class Adjustment(amount: Int)

class ProductionPlan (__production: Int) {
  private var _initialProduction = __production
  private var _productionAccumulator = 0
  private var _adjustments = List[Adjustment]()

  def production: Int = {
    assert(_productionAccumulator == calculateProductionAccumulator)
    _initialProduction + _productionAccumulator
  }

  def calculateProductionAccumulator: Int = {
    _adjustments.foldLeft(0)((sum, a) => sum + a.amount)
  }

  def applyAdjustment(anAdjustment: Adjustment): Unit = {
    _adjustments = anAdjustment :: _adjustments
    _productionAccumulator += anAdjustment.amount
  }
}

// main
@main def main(): Unit = {
  val plan = new ProductionPlan(10)
  plan.applyAdjustment(Adjustment(10))
  plan.applyAdjustment(Adjustment(20))
  assert(plan.production == 40)
}
