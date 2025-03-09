package org.sangho.refac2scala
package ch08_2

import java.util.Date

case class CustomerContract(startDate: Date)

case class Amount(value: Double) {
  def subtract(amount: Amount): Amount = Amount(value - amount.value)
  def multiply(rate: Double): Amount = Amount(value * rate)
}

class Customer(_name: String, __discountRate: Double) {
  val name = _name
  private var _discountRate = applyDiscountRate(__discountRate)
  val contract: CustomerContract = CustomerContract(new Date())

  private def applyDiscountRate(discountRate: Double): Double = discountRate

  def discountRate(): Double = _discountRate

  def setDiscountRate(number: Double): Unit = {
    _discountRate = applyDiscountRate(number)
  }

  def becomePreferred(): Unit = {
    setDiscountRate(_discountRate + 0.03)
  }

  def applyDiscount(amount: Amount): Amount = amount.subtract(amount.multiply(_discountRate))
}


// main
@main def main(): Unit = {
  val customer = new Customer("고객1", 0.1)
  val actual = customer.applyDiscount(Amount(100.0))
  assert(actual == Amount(90.0))
}
