package org.sangho.refac2scala
package ch04

class ProvinceTest extends munit.FunSuite:
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

  test("shortfall") {
    val asia = sampleProvinceData
    assertEquals(asia.shortfall, 5)
  }

  test("profit") {
    val asia = sampleProvinceData
    assertEquals(asia.profit, 230)
  }
