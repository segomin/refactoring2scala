package org.sangho.refac2scala
package ch08_6

case class Order(units: Int, isRepeat: Boolean)

case class PricingPlan(base: Double, unit: Double, discountThreshold: Int, discountFactor: Double) {
  def calculateCharge(order: Order) = {
    val charge = this.base + order.units * this.unit

    val discountableUnits = Math.max(order.units - this.discountThreshold, 0)
    var discount = discountableUnits * this.discountFactor
    if (order.isRepeat) discount += 20
    charge - discount
  }
}

def retrievePricingPlan(): PricingPlan = {
  PricingPlan(base = 10, unit = 1, discountThreshold = 100, discountFactor = 0.1)
}
def retrieveOrder(): Order = {
  Order(units = 100, isRepeat = true)
}
def chargeOrder(charge: Double): String = {
  s"Charge: $charge"
}


@main def main(): Unit = {
  val order = retrieveOrder()
  val pricingPlan = retrievePricingPlan()
  val finalCharge = pricingPlan.calculateCharge(order)
  val actual = chargeOrder(finalCharge)

  assert(actual == "Charge: 90.0")
}
