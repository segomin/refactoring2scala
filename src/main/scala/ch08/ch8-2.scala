package org.sangho.refac2scala
package ch08_2

import java.util.Date

case class CustomerContract(startDate: Date)

case class Amount(value: Double) {
  def subtract(amount: Amount): Amount = Amount(value - amount.value)
  def multiply(rate: Double): Amount = Amount(value * rate)
}

class Customer(val name: String, private var _discountRate: Double) {
  val contract: CustomerContract = CustomerContract(new Date())

  def discountRate(): Double = _discountRate

  def becomePreferred(): Unit = {
    _discountRate += 0.03
  }

  def applyDiscount(amount: Amount): Amount = amount.subtract(amount.multiply(_discountRate))
}


// main
@main def main(): Unit = {
  val customer = new Customer("고객1", 0.1)
  val actual = customer.applyDiscount(Amount(100.0))
  assert(actual == Amount(90.0))
}
