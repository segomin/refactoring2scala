package org.sangho.refac2scala
package ch11_3

import java.time.LocalDate

case class Order(placedOn: LocalDate, deliveryState: String)

def rushDeliveryDate(order: Order) = {
  var deliveryTime: Int = 0
  if (List("MA", "CT").contains(order.deliveryState))
    deliveryTime = 1
  else if (List("NY", "NH").contains(order.deliveryState))
    deliveryTime = 2
  else
    deliveryTime = 3
  order.placedOn.plusDays(1 + deliveryTime);
}
def regularDeliveryDate(order: Order) = {
  var deliveryTime: Int = 0
  if (List("MA", "CT", "NY").contains(order.deliveryState))
    deliveryTime = 2
  else if (List("ME", "NH").contains(order.deliveryState))
    deliveryTime = 3
  else
    deliveryTime = 4
  order.placedOn.plusDays(2 + deliveryTime);
}

// main
@main def main(): Unit = {
  val today = LocalDate.of(2025, 3, 30)
  assert(rushDeliveryDate(Order(today, "MA")) == today.plusDays(2))
  assert(rushDeliveryDate(Order(today, "CT")) == today.plusDays(2))
  assert(rushDeliveryDate(Order(today, "NY")) == today.plusDays(3))
  assert(rushDeliveryDate(Order(today, "TX")) == today.plusDays(4))
  assert(rushDeliveryDate(Order(today, "ME")) == today.plusDays(4))
  assert(regularDeliveryDate(Order(today, "MA")) == today.plusDays(4))
  assert(regularDeliveryDate(Order(today, "NY")) == today.plusDays(4))
  assert(regularDeliveryDate(Order(today, "ME")) == today.plusDays(5))
  assert(regularDeliveryDate(Order(today, "NH")) == today.plusDays(5))
  assert(regularDeliveryDate(Order(today, "TX")) == today.plusDays(6))
}
