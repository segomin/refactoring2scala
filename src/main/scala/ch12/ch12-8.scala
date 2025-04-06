package ch12_8

import org.scalatest.Assertions.*

class Party(val name: String)

class Employee(name: String, val id: String, val monthlyCost: Int) extends Party(name) {
  def annualCost: Int = monthlyCost * 12
}

class Department(name: String, val staff: List[Employee]) extends Party(name) {
  def monthlyCost: Int = staff.map(_.monthlyCost).sum
  def headCount: Int = staff.size
  def annualCost: Int = monthlyCost * 12
}

// main
@main def main() = {
  val employee1 = new Employee("John Doe", "E123", 1000)
  val employee2 = new Employee("Jane Doe", "E456", 2000)
  val department = new Department("HR", List(employee1, employee2))

  assertResult(3000)(department.monthlyCost)
  assertResult(2)(department.headCount)
  assertResult(36000)(department.annualCost)

  // Test the annual cost of an individual employee
  assertResult(12000)(employee1.annualCost)
  assertResult(24000)(employee2.annualCost)
//  assert(john.isMale)
//  assertResult(jane.genderCode)(persons(1).genderCode)
//  assertResult(person.genderCode)(persons(2).genderCode)
}
