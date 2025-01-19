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
  val amount: Int = amountFor(this)
  val volumeCredits: Int = volumeCreditsFor(this)


  def amountFor(perf: PerformancePlay): Int = {
    var result = 0
    perf.play.kind match {
      case PlayType.TRAGEDY =>
        result = 40000
        if (perf.audience > 30) result += 1000 * (perf.audience - 30)

      case PlayType.COMEDY =>
        result = 30000
        if (perf.audience > 20) result += 10000 + 500 * (perf.audience - 20)
        result += 300 * perf.audience
    }
    result
  }

  def volumeCreditsFor(perf: PerformancePlay): Int = {
    // 포인트를 적립한다.
    var result = 0
    result += Math.max(perf.audience - 30, 0)
    // 희극 관객 5명마다 추가 포인트를 제공핟나.
    if (perf.play.kind.eq(PlayType.COMEDY))
      result += floor(perf.audience / 5).toInt
    result
  }
}

class Plays(plays: (String, Play)*) {
  private val playMap: Map[String, Play] = Map(plays *)
  def get(performance: String): Play = playMap(performance)
}

case class StatementData(customer: String, performances: List[PerformancePlay], plays: Plays)

def enhancedPerformance(perf: Performance, plays: Plays): PerformancePlay = new PerformancePlay(perf, plays)

class Statement {

  def statement(invoice: Invoice, plays: Plays): String = {
    val data = StatementData(invoice.customer, invoice.performances.map(perf => enhancedPerformance(perf, plays)), plays)
    renderPlainText(data)
  }

  def renderPlainText(data: StatementData): String = {
    val result = new StringBuilder(s"청구내역 (고객명: ${data.customer})\n")

    for (performance <- data.performances) {
      // 청구 내역을 출력한다.
      result.append(s"${performance.play.name}: ${performance.amount.usd} (${performance.perf.audience}석)\n")
    }

    def totalAmount = {
      var result = 0
      for (performance <- data.performances) {
        result += performance.amount
      }
      result
    }

    def totalVolumeCredits = {
      var result = 0
      for (performance <- data.performances) {
        result += performance.volumeCredits
      }
      result
    }

    result.append(s"총액: ${totalAmount.usd}\n")
    result.append(s"적립 포인트: ${totalVolumeCredits}점")
    result.toString
  }

  extension (amount: Int)
    private def usd: String = "$%,.2f".format((amount / 100).toDouble)
}

