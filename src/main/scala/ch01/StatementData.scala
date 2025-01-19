package org.sangho.refac2scala
package ch01

import scala.math.*

case class Performance(playID: String, audience: Int)

case class Invoice(customer: String, performances: List[Performance])

// Scala 에서 Enumeration 을 사용하는 방법
object PlayType extends Enumeration {
  type Kind = Value
  val TRAGEDY, COMEDY = Value
}

case class Play(name: String, kind: PlayType.Kind)

class PerformancePlay(val perf: Performance, plays: Plays) {
  val play: Play = plays.get(perf.playID)
  val audience: Int = perf.audience
  val amount: Int = PerformanceCalculatorCreator.calculatorOf(play).amountFor(perf)
  val volumeCredits: Int = PerformanceCalculatorCreator.calculatorOf(play).volumeCreditsFor(perf)
}

class Plays(plays: (String, Play)*) {
  private val playMap: Map[String, Play] = Map(plays *)

  def get(performance: String): Play = playMap(performance)
}

class StatementData(invoice: Invoice, val plays: Plays) {
  val customer: String = invoice.customer
  val performances: List[PerformancePlay] = invoice.performances.map(perf => PerformancePlay(perf, plays))
  val totalAmount: Int = totalAmountFor(performances)
  val totalVolumeCredits: Int = totalVolumeCreditsFor(performances)

  private def totalAmountFor(performances: List[PerformancePlay]) = {
    var result = 0
    for (performance <- performances) {
      result += performance.amount
    }
    result
  }

  private def totalVolumeCreditsFor(performances: List[PerformancePlay]) = {
    var result = 0
    for (performance <- performances) {
      result += performance.volumeCredits
    }
    result
  }
}

