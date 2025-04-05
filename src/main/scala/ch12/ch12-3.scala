package org.sangho.refac2scala
package ch12_3

import org.scalatest.Assertions._


abstract class Party {}
class Employee(val name: String, val id: String, val monthlyCost: Int) extends Party() {}
class Department(val name: String, val monthlyCost: Int) extends Party() {}

// main
@main def main() = {
  val employee = new Employee("John Doe", "E123", 1000)
  assertResult("John Doe")(employee.name)
  assertResult("E123")(employee.id)
  assertResult(1000)(employee.monthlyCost)
  val department = new Department("HR", 2000)
  assertResult("HR")(department.name)
  assertResult(2000)(department.monthlyCost)
  assertResult(24000)(department.monthlyCost * 12)
}
