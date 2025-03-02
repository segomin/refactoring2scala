package org.sangho.refac2scala
package ch07_3


enum Priority(val value: Int) {
  case Normal extends Priority(1);
  case High extends Priority(2)
  case Rush extends Priority(3)
  def higherThan(other: Priority): Boolean = this match {
    case Normal => other.value < Normal.value
    case High => other.value < High.value
    case Rush => other.value < Rush.value
  }
}

class Order(data: Map[String, String]) {
  val name = data("name")
  val priority: Priority = data("priority") match {
    case "normal" => Priority.Normal
    case "high" => Priority.High
    case "rush" => Priority.Rush
  }
}

@main def main(): Unit = {
  val orders = List(
    new Order(Map("name" -> "apple", "priority" -> "normal")),
    new Order(Map("name" -> "banana", "priority" -> "high")))

  val highPriorityCount = orders.count(order => order.priority.higherThan(Priority.Normal))
  assert(highPriorityCount == 1)
}