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

class Plays(plays: (Performance, Play)*) {
  private val playMap: Map[Performance, Play] = Map(plays *)
  def get(performance: Performance): Play = playMap(performance)
}

class Statement {
  def statement(invoice: Invoice, plays: Plays): String = {
    var totalAmount = 0
    var volumeCredit = 0
    val result = new StringBuilder(s"청구내역 (고객명: ${invoice.customer})\n")

    def amountFor(perf: Performance): Int = {
      var result = 0
      playFor(perf).kind match {
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

    def playFor(performance: Performance): Play = plays.get(performance)

    def volumeCreditsFor(perf: Performance): Int = {
      // 포인트를 적립한다.
      var result = 0
      result += Math.max(perf.audience - 30, 0)
      // 희극 관객 5명마다 추가 포인트를 제공핟나.
      if (playFor(perf).kind.eq(PlayType.COMEDY))
        result += floor(perf.audience / 5).toInt
      result
    }

    for (performance <- invoice.performances) {
      volumeCredit += volumeCreditsFor(performance)
      // 청구 내역을 출력한다.
      result.append(s"${playFor(performance).name}: ${amountFor(performance).toDollar} (${performance.audience}석)\n")
      totalAmount += amountFor(performance)
    }
    result.append(s"총액: ${totalAmount.toDollar}\n")
    result.append(s"적립 포인트: ${volumeCredit}점")
    result.toString
  }

  extension (amount: Int)
    private def toDollar: String = "$%,.2f".format((amount / 100).toDouble)
}

