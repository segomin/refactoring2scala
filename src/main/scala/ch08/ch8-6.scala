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

def calculateCharge(order: Order, pricingPlan: PricingPlan) = {
  val chargePerUnit = pricingPlan.unit
  val units = order.units
  val charge = pricingPlan.base + units * chargePerUnit
  val discountableUnits = Math.max(units - pricingPlan.discountThreshold, 0)
  var discount = discountableUnits * pricingPlan.discountFactor
  if (order.isRepeat) discount += 20
  val finalCharge = charge - discount
  finalCharge
}

@main def main(): Unit = {
  val order = retrieveOrder()
  val pricingPlan = retrievePricingPlan()
  val finalCharge = calculateCharge(order, pricingPlan)
  val actual = chargeOrder(finalCharge)

  assert(actual == "Charge: 90.0")
}
