package org.sangho.refac2scala
package ch10_3

case class Amount(amount: Int, reasonCode: String)

case class Employee(isSeparated: Boolean, isRetired: Boolean, salary: Int)

def payAmount(employee: Employee): Amount = {
  var result: Amount = null

  def someFinalComputation(employee: Employee) = {
    Amount(employee.salary, "NOR")
  }

  if (employee.isSeparated) return Amount(0, "SEP")
  if (employee.isRetired) return Amount(0, "RET")
  // 급여 계산 로직
  result = someFinalComputation(employee)
  result
}

case class Instrument(capital: Int, income: Int, duration: Int, adjustmentFactor: Int, interestRate: Int)

def adjustedCapital(instrument: Instrument): Int = {
  var result = 0
  if (instrument.capital > 0) {
    if (instrument.interestRate > 0 && instrument.duration > 0) {
      result = (instrument.income / instrument.duration) * instrument.adjustmentFactor
    }
  }
  result
}

// main
@main def main(): Unit = {
  assert(payAmount(Employee(true, false, 1000)) == Amount(0, "SEP"))
  assert(payAmount(Employee(false, true, 1000)) == Amount(0, "RET"))
  assert(payAmount(Employee(false, false, 1000)) == Amount(1000, "NOR"))

  // 조건 반대로 만들기
  assert(adjustedCapital(Instrument(1, 100, 10, 2, 5)) == 20)
  assert(adjustedCapital(Instrument(1, 100, 10, 2, 0)) == 0)
  assert(adjustedCapital(Instrument(1, 100, 0, 2, 5)) == 0)
  assert(adjustedCapital(Instrument(0, 100, 10, 2, 5)) == 0)
}
