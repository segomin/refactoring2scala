package org.sangho.refac2scala
package ch11_3_2

import java.time.LocalDate

case class Order(placedOn: LocalDate, deliveryState: String)

def deliveryDate(order: Order, isRush: Boolean): LocalDate = {
  var result: LocalDate = null
  var deliveryTime: Int = 0
  if (order.deliveryState == "NA" || order.deliveryState == "CT") {
    deliveryTime = if isRush then 1 else 2
  } else if (order.deliveryState == "NY" || order.deliveryState == "NH") {
    deliveryTime = 2
    if (order.deliveryState == "NH" && !isRush) {
      deliveryTime = 3
    }
  } else if (isRush) {
    deliveryTime = 3
  } else if (order.deliveryState == "ME") {
    deliveryTime = 3
  } else {
    deliveryTime = 4
  }
  result = order.placedOn.plusDays(2 + deliveryTime)
  if (isRush) result = result.minusDays(1)
  result
}

// main
@main def main(): Unit = {
  val today = LocalDate.of(2025, 3, 30)
  assert(deliveryDate(Order(today, "MA"), true) == today.plusDays(4))
  assert(deliveryDate(Order(today, "CT"), true) == today.plusDays(2))
  assert(deliveryDate(Order(today, "NY"), true) == today.plusDays(3))
  assert(deliveryDate(Order(today, "TX"), true) == today.plusDays(4))
  assert(deliveryDate(Order(today, "ME"), true) == today.plusDays(4))
  assert(deliveryDate(Order(today, "MA"), false) == today.plusDays(6))
  assert(deliveryDate(Order(today, "NY"), false) == today.plusDays(4))
  assert(deliveryDate(Order(today, "ME"), false) == today.plusDays(5))
  assert(deliveryDate(Order(today, "NH"), false) == today.plusDays(5))
  assert(deliveryDate(Order(today, "TX"), false) == today.plusDays(6))
}
