package org.sangho.refac2scala
package ch09_1

// main
@main def main(): Unit = {
  val height = 10
  val width = 20

  var temp = 2 * (height + width)
  println(temp)
  temp = height + width
  println(temp)
}
