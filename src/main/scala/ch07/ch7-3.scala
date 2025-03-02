package org.sangho.refac2scala
package ch07_3

class Order(data: Map[String, String]) {
  val name = data("name")
  val priority = data("priority")
}

@main def main(): Unit = {
  val orders = List(
    new Order(Map("name" -> "apple", "priority" -> "normal")),
    new Order(Map("name" -> "banana", "priority" -> "high")))

  val highPriorityCount = orders.count(order => order.priority == "high")
  assert(highPriorityCount == 1)
}