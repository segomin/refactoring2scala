package org.sangho.refac2scala
package ch10_1

case class Date(timestamp: Long) {
  def isBefore(other: Date): Boolean = timestamp < other.timestamp

  def isAfter(other: Date): Boolean = timestamp > other.timestamp
}

case class Plan(summerStart: Date, summerEnd: Date, summerRate: Int, regularRate: Int, regularServiceCharge: Int)

def chargeOf(date: Date, plan: Plan, quantity: Int): Int = {
  def summer = !date.isBefore(plan.summerStart) && !date.isAfter(plan.summerEnd)
  def summerCharge = quantity * plan.summerRate
  def regularCharge = quantity * plan.regularRate + plan.regularServiceCharge

  if (summer) summerCharge else regularCharge
}

// main
@main def main(): Unit = {
  val plan = Plan(Date(100), Date(200), 10, 20, 30)
  assert(chargeOf(Date(150), plan, 10) == 100)
  assert(chargeOf(Date(250), plan, 10) == 230)
}
