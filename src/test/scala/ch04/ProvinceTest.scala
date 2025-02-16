package org.sangho.refac2scala
package ch04

import scala.compiletime.uninitialized

class ProvinceTest extends munit.FunSuite:
  var asia: Province = uninitialized

  override def beforeAll(): Unit = {
    asia = sampleProvinceData
  }

  test("shortfall") {
    assertEquals(asia.shortfall, 5)
  }

  test("profit") {
    assertEquals(asia.profit, 230)
  }

  test("zero demand") {
    asia.demand = 0
    assertEquals(asia.shortfall, -25)
    assertEquals(asia.profit, 0)
  }

  test("negative demand") {
    asia.demand = -1
    assertEquals(asia.shortfall, -26)
    assertEquals(asia.profit, -10)
  }

class ProvinceWithNoProducerTest extends munit.FunSuite:
  var asia: Province = uninitialized

  override def beforeAll(): Unit = {
    asia = Province("No producers", 30, 20)
  }

  test("shortfall") {
    assertEquals(asia.shortfall, 30)
  }

  test("profit") {
    assertEquals(asia.profit, 0)
  }

def sampleProvinceData: Province = {
  val producers = List(
    Producer("Byzantium", 10, 9),
    Producer("Attalia", 12, 10),
    Producer("Sinope", 10, 6),
  )
  val asia = Province("Asia", 30, 20)
  for (producer <- producers) {
    asia.addProducer(producer)
  }
  asia
}