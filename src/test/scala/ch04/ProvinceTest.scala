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