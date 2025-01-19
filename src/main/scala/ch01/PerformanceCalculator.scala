package org.sangho.refac2scala
package ch01

import scala.math.*

trait PerformanceCalculator:
  def amountFor(perf: Performance): Int
  def volumeCreditsFor(perf: Performance): Int

object PerformanceCalculatorCreator:
  def calculatorOf(play: Play): PerformanceCalculator = {
    play.kind match {
      case PlayType.TRAGEDY => TragedyCalculator
      case PlayType.COMEDY => ComedyCalculator
    }
  }

object TragedyCalculator extends PerformanceCalculator:
  def amountFor(perf: Performance): Int = {
    var result = 40000
    if (perf.audience > 30) result += 1000 * (perf.audience - 30)
    result
  }

  def volumeCreditsFor(perf: Performance): Int = {
    // 포인트를 적립한다.
    var result = 0
    result += Math.max(perf.audience - 30, 0)
    result
  }

object ComedyCalculator extends PerformanceCalculator:
  def amountFor(perf: Performance): Int = {
    var result = 30000
    if (perf.audience > 20) result += 10000 + 500 * (perf.audience - 20)
    result += 300 * perf.audience
    result
  }

  def volumeCreditsFor(perf: Performance): Int = {
    // 포인트를 적립한다.
    var result = 0
    result += Math.max(perf.audience - 30, 0)
    // 희극 관객 5명마다 추가 포인트를 제공핟나.
    result += floor(perf.audience / 5).toInt
    result
  }
