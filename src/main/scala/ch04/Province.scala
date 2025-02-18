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
                val demand: Int,
                val price: Int,
                val totalProduction: Int = 0,
                val producers: List[Producer] = List()
              ) {

  def addProducer(producer: Producer) = {
    producer.province = this
    copy(name, demand, price, totalProduction + producer.production, producer :: producers)
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

  def copy(name: String = this.name,
           demand: Int = this.demand,
           price: Int = this.price,
           totalProduction: Int = this.totalProduction,
           producers: List[Producer] = this.producers) = {
    new Province(name, demand, price, totalProduction, producers)
  }
}

/**
 * 생산자
 */
class Producer(val name: String, val cost: Int, val production: Int = 0) {
  var province: Province = _

  def profit() = production - cost
}
