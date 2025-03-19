package org.sangho.refac2scala
package ch09_1

// main
@main def main(): Unit = {
  val height = 10
  val width = 20

  val perimeter = 2 * (height + width)
  println(perimeter)
  val area = height + width
  println(area)
}
