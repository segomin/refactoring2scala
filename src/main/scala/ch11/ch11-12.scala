package org.sangho.refac2scala
package ch11_12

import org.scalatest.Assertions._

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

class OrderProcessingError(val code: Int) extends Exception(s"주문 처리 오류: $code") {
  def name = "OrderProcessingError"
}

def localShippingRules(country: String): ShippingRules = {
  val data = countryData.shippingRules.getOrElse(country, null)
  if (data != null) {
    ShippingRules(data)
  } else {
    throw new OrderProcessingError(-23)
  }
}

def calculateShippingCosts(order: Order): Double = {
  val shippingRules = localShippingRules(order.country)
  shippingRules.shippingCosts(order.shippingCosts)
}

// main
@main def main() = {
  // 최상위 로직 생략
  // val status = calculateShippingCosts(Order("US", 100))
  // if (status.isLeft) { errList.push(...) }
  assert(calculateShippingCosts(Order("US", 100)) == 100.0)
  assert(calculateShippingCosts(Order("CA", 100)) == 150.0)
  assert(calculateShippingCosts(Order("KR", 100)) == 200.0)
  // should throw an error
  assertThrows[OrderProcessingError] {
    calculateShippingCosts(Order("XX", 100))
  }
}
