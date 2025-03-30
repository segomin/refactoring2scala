package org.sangho.refac2scala
package ch11_12

case class ShippingRules(data: String) {
  def shippingCosts(cost: Double): Double = {
    data match {
      case "Rule01" => cost * 1.0
      case "Rule02" => cost * 1.5
      case "Rule03" => cost * 2.0
      case _ => cost
    }
  }
}

object ShippingRules {
  val shippingRules: Map[String, String] = Map(
    "US" -> "Rule01",
    "CA" -> "Rule02",
    "KR" -> "Rule03",
  )
}

class CountryData(val shippingRules: Map[String, String])

val countryData = new CountryData(ShippingRules.shippingRules)

case class Order(country: String, shippingCosts: Double)

def localShippingRules(country: String): Either[Int, ShippingRules] = {
  val data = countryData.shippingRules.getOrElse(country, null)
  if (data != null) {
    Right(ShippingRules(data))
  } else {
    Left(-23)
  }
}

def calculateShippingCosts(order: Order): Either[Int, Double] = {
  val shippingRules = localShippingRules(order.country)
  shippingRules match
    case Right(rules) => Right(rules.shippingCosts(order.shippingCosts))
    case Left(errorCode) => Left(errorCode)
}

// main
@main def main() = {
  assert(calculateShippingCosts(Order("US", 100)) == Right(100.0))
  assert(calculateShippingCosts(Order("CA", 100)) == Right(150.0))
  assert(calculateShippingCosts(Order("KR", 100)) == Right(200.0))
  assert(calculateShippingCosts(Order("XX", 100)) == Left(-23))
}
