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
  val baseCharge = pricingPlan.base
  val order = retrieveOrder()
  val units = order.units
  val chargePerUnit = pricingPlan.unit
  val charge = baseCharge + units * chargePerUnit
  val discountableUnits = Math.max(units - pricingPlan.discountThreshold, 0)
  var discount = discountableUnits * pricingPlan.discountFactor
  if (order.isRepeat) discount += 20
  val finalCharge = charge - discount
  val actual = chargeOrder(finalCharge)

  assert(actual == "Charge: 90.0")
}
