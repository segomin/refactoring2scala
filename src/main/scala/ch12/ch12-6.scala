package ch12_6

import org.scalatest.Assertions.*

class Employee(val name: String, val kind: String) {
  require(kind == "engineer" || kind == "manager" || kind == "salesperson",
    s"${kind}라는 직원 유형은 없습니다.")

  override def toString = s"$name ($kind)"
}

// main
@main def main() = {
  val engineer = new Employee("Alice", "engineer")
  val manager = new Employee("Bob", "manager")
  val salesperson = new Employee("Charlie", "salesperson")

  assertResult("Alice (engineer)")(engineer.toString)
  assertResult("Bob (manager)")(manager.toString)
  assertResult("Charlie (salesperson)")(salesperson.toString)

  assertThrows[IllegalArgumentException] {
    new Employee("Dave", "invalid")
  }
}
