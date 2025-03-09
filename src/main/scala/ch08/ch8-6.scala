package org.sangho.refac2scala
package ch08_6

case class Order(units: Int, isRepeat: Boolean)

case class PricingPlan(base: Double, unit: Double, discountThreshold: Int, discountFactor: Double)

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
  val pricingPlan = retrievePricingPlan()
  val order = retrieveOrder()
  val baseCharge = pricingPlan.base
  var charge: Double = 0.0
  val chargePerUnit = pricingPlan.unit
  val units = order.units
  var discount: Double = 0.0
  charge = baseCharge + units * chargePerUnit
  val discountableUnits = Math.max(units - pricingPlan.discountThreshold, 0)
  discount = discountableUnits * pricingPlan.discountFactor
  if (order.isRepeat) discount += 20
  charge = charge - discount
  val actual = chargeOrder(charge)

  assert(actual == "Charge: 90.0")
}
