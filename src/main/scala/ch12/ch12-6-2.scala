package ch12_6_2

import org.scalatest.Assertions.*

class Employee(val name: String, private var _kind: String) {
  validateType(_kind)

  def kind: String = _kind

  def kind_=(newKind: String): Unit = {
    validateType(newKind)
    _kind = newKind
  }

  private def validateType(kind: String): Unit = {
    require(kind == "engineer" || kind == "manager" || kind == "salesperson",
      s"${kind}라는 직원 유형은 없습니다.")
  }

  def capitalizedKind: String = {
    _kind.capitalize
  }

  override def toString = s"$name ($capitalizedKind)"
}

// main
@main def main() = {
  val engineer = new Employee("Alice", "engineer")
  val manager = new Employee("Bob", "manager")
  val salesperson = new Employee("Charlie", "salesperson")

  assertResult("Alice (Engineer)")(engineer.toString)
  assertResult("Bob (Manager)")(manager.toString)
  assertResult("Charlie (Salesperson)")(salesperson.toString)

  assertThrows[IllegalArgumentException] {
    new Employee("Dave", "invalid")
  }
}
