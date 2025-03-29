package org.sangho.refac2scala
package ch11_5

class Order(val quantity: Int, val itemPrice: Double) {
  def finalPrice(): Double = {
    val basePrice = this.quantity * this.itemPrice
    val discountLevel = if (basePrice > 100) 2 else 1
    this.discountPrice(basePrice, discountLevel)
  }

  def discountPrice(basePrice: Double, discountLevel: Int): Double = {
    discountLevel match
      case 1 => basePrice * 0.95
      case 2 => basePrice * 0.9
  }
}


// main
@main def main(): Unit = {
  assert(new Order(10, 10).finalPrice() == 95.0)
  assert(new Order(20, 10).finalPrice() == 180.0)
}
