package org.sangho.refac2scala
package ch09_5

case class Customer(id: String)

class Order(data: Map[String, String]) {
  private var _number = data("number")
  private var _customer = Customer(data("customer"))

  def number: String = _number
  def customer: Customer = _customer
}

// main
@main def main(): Unit = {
  val order = new Order(Map("number" -> "1234", "customer" -> "king"))
  assert(order.number == "1234")
  assert(order.customer == Customer("king"))
}
