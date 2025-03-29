package org.sangho.refac2scala
package ch11_3

import java.time.LocalDate

case class Order(placedOn: LocalDate, deliveryState: String)

def deliveryDate(order: Order, isRush: Boolean): LocalDate = {
  if (isRush) {
    var deliveryTime: Int = 0
    if (List("MA", "CT").contains(order.deliveryState))
      deliveryTime = 1
    else if (List("NY", "NH").contains(order.deliveryState))
      deliveryTime = 2
    else
      deliveryTime = 3
    order.placedOn.plusDays(1 + deliveryTime);
  } else {
    var deliveryTime: Int = 0
    if (List("MA", "CT", "NY").contains(order.deliveryState))
      deliveryTime = 2
    else if (List("ME", "NH").contains(order.deliveryState))
      deliveryTime = 3
    else
      deliveryTime = 4
    order.placedOn.plusDays(2 + deliveryTime);
  }
}


// main
@main def main(): Unit = {
  val today = LocalDate.of(2025, 3, 30)
  assert(deliveryDate(Order(today, "MA"), true) == today.plusDays(2))
  assert(deliveryDate(Order(today, "CT"), true) == today.plusDays(2))
  assert(deliveryDate(Order(today, "NY"), true) == today.plusDays(3))
  assert(deliveryDate(Order(today, "TX"), true) == today.plusDays(4))
  assert(deliveryDate(Order(today, "ME"), true) == today.plusDays(4))
  assert(deliveryDate(Order(today, "MA"), false) == today.plusDays(4))
  assert(deliveryDate(Order(today, "NY"), false) == today.plusDays(4))
  assert(deliveryDate(Order(today, "ME"), false) == today.plusDays(5))
  assert(deliveryDate(Order(today, "NH"), false) == today.plusDays(5))
  assert(deliveryDate(Order(today, "TX"), false) == today.plusDays(6))
}
