package org.sangho.refac2scala
package ch04

/**
 * 생산 계획은 각 지역(Province)의 수요(demand)와 가격(price)으로 구정
 * 생산자들은 각기 제품을 특정 가격으로 특정 수량만큼 생산
 * 생산자 별로 제품을 모두 판매시 수익: full revenue
 */

/**
 * 지역
 */
class Province(
                val name: String,
                var demand: Int,
                var price: Int,
              ) {
  var totalProduction: Int = 0
  var producers: List[Producer] = List()

  def addProducer(producer: Producer) = {
    producer.province = this
    producers = producer :: producers
    totalProduction += producer.production
  }

  def shortfall: Int = demand - totalProduction

  def profit: Int = demandValue - demandCost

  def demandValue: Int = satisfiedDemand * price

  def satisfiedDemand: Int = Math.min(demand, totalProduction)

  def demandCost: Int = {
    producers.foldLeft((demand, 0)) { case ((remainingDemand, result), p) =>
      val contribution = Math.min(remainingDemand, p.production)
      (remainingDemand - contribution, result + contribution * p.cost)
    }._2
  }
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
