package org.sangho.refac2scala
package ch08_2

import java.util.Date

case class CustomerContract(startDate: Date, discountRate: Double = 0.0)

case class Amount(value: Double) {
  def subtract(amount: Amount): Amount = Amount(value - amount.value)
  def multiply(rate: Double): Amount = Amount(value * rate)
}

class Customer(_name: String, __discountRate: Double) {
  val name = _name
  var contract: CustomerContract = CustomerContract(new Date(), __discountRate)

  def discountRate(): Double = contract.discountRate

  def setDiscountRate(number: Double): Unit = {
    contract = contract.copy(discountRate = number)
  }

  def becomePreferred(): Unit = {
    setDiscountRate(discountRate() + 0.03)
  }

  def applyDiscount(amount: Amount): Amount = amount.subtract(amount.multiply(discountRate()))
}


// main
@main def main(): Unit = {
  val customer = new Customer("고객1", 0.1)
  val actual = customer.applyDiscount(Amount(100.0))
  assert(actual == Amount(90.0))
}
