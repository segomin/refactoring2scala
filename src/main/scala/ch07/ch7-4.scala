package org.sangho.refac2scala
package ch07_4

case class Item(name: String, price: Int)

class Order(val quantity: Int, val item: Item) {
  def price: Double = {
    val basePrice = quantity * item.price
    var discountFactor = 0.98
    if (basePrice > 1000) discountFactor -= 0.03
    basePrice * discountFactor
  }
}

@main def main(): Unit = {
  assert(new Order(10, Item("apple", 100)).price == 980.0)
  assert(new Order(10, Item("gold", 15000)).price == 142500.0)
}