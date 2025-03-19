package org.sangho.refac2scala
package ch09_6

case class Customer(id: String)

class RepositoryData {
  private var _customers = Map[String, Customer]()

  def contains(id: String): Boolean = _customers.contains(id)

  def registerCustomer(id: String): Customer = {
    if (!contains(id))
      _customers += (id -> Customer(id))
    findCustomer(id)
  }

  def findCustomer(id: String): Customer = _customers(id)
}

// main
@main def main(): Unit = {
  val repositoryData = RepositoryData()
  repositoryData.registerCustomer("king")
  assert(repositoryData.findCustomer("king") == Customer("king"))
}
