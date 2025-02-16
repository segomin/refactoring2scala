package org.sangho.refac2scala
package ch04

/**
 * 생산 계획은 각 지역(Province)의 수요(demand)와 가격(price)으로 구정
 * 생산자들은 각기 제품을 특정 가격으로 특정 수량만큼 생산
 * 생산ㅈ자 별로 제품을 모두 판매시 수익: full revenue
 *
 */

/**
 * 지역
 */
class Province(
                val name: String,
                _producers: List[Producer],
                var totalProduction: Int = 0,
                var demand: Int,
                var price: Int,
              ) {
  var producers: List[Producer] = _producers.map(p => {
    p.province = (this)
    p
  })

  def shortfall: Int = demand - totalProduction
}

/**
  * 생산자
  */
class Producer(val name: String, val cost: Int, var production: Int = 0) {
  var province: Province = _

  def profit() = production - cost

  def setProduction(amount: Int = 0) = {
    val newProduction = amount
    province.totalProduction += newProduction - production
    production = newProduction
  }
}

def sampleProvinceData: Province = {
  val producers = List(
    Producer("Byzantium", 10, 9),
    Producer("Attalia", 12, 10),
    Producer("Sinope", 10, 6),
  )
  Province("Asia", producers, 30, 21, 10)
}

@main def ch04(): Unit = {
  val asia = sampleProvinceData

  println(asia.shortfall)
}
