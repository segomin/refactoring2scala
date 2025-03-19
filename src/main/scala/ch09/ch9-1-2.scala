package org.sangho.refac2scala
package ch09_1_2

case class Scenario(primaryForce: Int, secondaryForce: Int, mass: Int, delay: Int)

def discount(inputValue: Int, quantity: Int): Int = {
  var result = inputValue
  if (inputValue >= 50) result -= 2
  if (quantity >= 100) result -= 1
  result
}

// main
@main def main(): Unit = {
  assert(discount(100, 10) == 98)
  assert(discount(100, 100) == 97)
  assert(discount(49, 10) == 49)
  assert(discount(49, 100) == 48)
}
