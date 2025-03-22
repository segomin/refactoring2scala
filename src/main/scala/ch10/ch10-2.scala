package org.sangho.refac2scala
package ch10_2

case class Employee(seniority: Int, monthsDisabled: Int, isPartTime: Boolean)

def disabilityAmount(employee: Employee): Int = {
  if (employee.seniority < 2) return 0
  if (employee.monthsDisabled > 12) return 0
  if (employee.isPartTime) return 0

  // 장애 수당 계산
  employee.seniority + 100
}

// main
@main def main(): Unit = {
  assert(disabilityAmount(Employee(1, 12, false)) == 0)
  assert(disabilityAmount(Employee(2, 13, false)) == 0)
  assert(disabilityAmount(Employee(2, 12, true)) == 0)
  assert(disabilityAmount(Employee(2, 12, false)) == 102)
}
