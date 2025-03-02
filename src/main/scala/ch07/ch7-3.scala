package org.sangho.refac2scala
package ch07_3

enum Priority:
  case Normal, High, Rush

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

  val highPriorityCount = orders.count(order => order.priority == Priority.High || order.priority == Priority.Rush)
  assert(highPriorityCount == 1)
}