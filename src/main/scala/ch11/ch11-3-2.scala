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
def rushDeliveryDate(order: Order) = {
  deliveryDate(order, true)
}
def regularDeliveryDate(order: Order) = {
  deliveryDate(order, false)
}

// main
@main def main(): Unit = {
  val today = LocalDate.of(2025, 3, 30)
  assert(rushDeliveryDate(Order(today, "MA")) == today.plusDays(4))
  assert(rushDeliveryDate(Order(today, "CT")) == today.plusDays(2))
  assert(rushDeliveryDate(Order(today, "NY")) == today.plusDays(3))
  assert(rushDeliveryDate(Order(today, "TX")) == today.plusDays(4))
  assert(rushDeliveryDate(Order(today, "ME")) == today.plusDays(4))
  assert(regularDeliveryDate(Order(today, "MA")) == today.plusDays(6))
  assert(regularDeliveryDate(Order(today, "NY")) == today.plusDays(4))
  assert(regularDeliveryDate(Order(today, "ME")) == today.plusDays(5))
  assert(regularDeliveryDate(Order(today, "NH")) == today.plusDays(5))
  assert(regularDeliveryDate(Order(today, "TX")) == today.plusDays(6))
}
