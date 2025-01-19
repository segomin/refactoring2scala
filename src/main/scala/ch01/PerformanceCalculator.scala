package org.sangho.refac2scala
package ch01

import scala.math.*

trait PerformanceCalculator:
  def amountFor(perf: PerformancePlay): Int
  def volumeCreditsFor(perf: PerformancePlay): Int

object PerformanceCalculator:
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


