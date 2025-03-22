package org.sangho.refac2scala
package ch10_2

case class Employee(seniority: Int, monthsDisabled: Int, isPartTime: Boolean, onVacation: Boolean = false)

def disabilityAmount(employee: Employee): Int = {
  def isNotEligibleForDisability = employee.seniority < 2
    || employee.monthsDisabled > 12
    || employee.isPartTime

  if (employee.onVacation && employee.seniority > 10) {
     return 1
  }

  if (isNotEligibleForDisability) return 0

  // 장애 수당 계산
  employee.seniority + 100
}

// main
@main def main(): Unit = {
  assert(disabilityAmount(Employee(1, 12, false)) == 0)
  assert(disabilityAmount(Employee(2, 13, false)) == 0)
  assert(disabilityAmount(Employee(2, 12, true)) == 0)
  assert(disabilityAmount(Employee(2, 12, false)) == 102)
  assert(disabilityAmount(Employee(11, 12, false, true)) == 1)
}
