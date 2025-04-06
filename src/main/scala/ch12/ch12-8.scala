package ch12_8

import org.scalatest.Assertions.*

abstract class Party(val name: String) {
  def monthlyCost: Int
  def annualCost: Int = monthlyCost * 12
}

class Employee(name: String, val id: String, val _monthlyCost: Int) extends Party(name) {
  override def monthlyCost: Int = _monthlyCost
}

class Department(name: String, val staff: List[Employee]) extends Party(name) {
  override def monthlyCost: Int = staff.map(_._monthlyCost).sum
  def headCount: Int = staff.size
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
}
