package org.sangho.refac2scala
package ch09_5

class RepositoryData {
  private var _customers = Map[String, Customer]()

  def contains(id: String): Boolean = _customers.contains(id)

  def registerCustomer(id: String): Unit = {
    if (!contains(id))
      _customers += (id -> Customer(id))
  }

  def findCustomer(id: String): Customer = _customers(id)
}

val repositoryData = RepositoryData()

def registerCustomer(id: String): Customer = {
  if (!repositoryData.contains(id))
    repositoryData.registerCustomer(id)
  repositoryData.findCustomer(id)
}

case class Customer(id: String)

class Order(data: Map[String, String]) {
  private var _number = data("number")
  private var _customer = registerCustomer(data("customer"))

  def number: String = _number
  def customer: Customer = _customer
}

// main
@main def main(): Unit = {
  val order = new Order(Map("number" -> "1234", "customer" -> "king"))
  assert(order.number == "1234")
  assert(order.customer == Customer("king"))
}
