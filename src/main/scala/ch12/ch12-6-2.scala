package ch12_6_2

import org.scalatest.Assertions.*

case class EmployeeType(value: String) {
  override def toString: String = value
}

class Employee(val name: String, private var _kind: EmployeeType) {
  validateType(_kind)

  def kind: EmployeeType = _kind

  def kind_=(newKind: EmployeeType): Unit = {
    validateType(newKind)
    _kind = newKind
  }

  private def validateType(kind: EmployeeType): Unit = {
    require(kind.value == "engineer" || kind.value == "manager" || kind.value == "salesperson",
      s"${kind}라는 직원 유형은 없습니다.")
  }

  def capitalizedKind: String = {
    _kind.value.capitalize
  }

  override def toString = s"$name ($capitalizedKind)"
}

// main
@main def main() = {
  val engineer = new Employee("Alice", EmployeeType("engineer"))
  val manager = new Employee("Bob", EmployeeType("manager"))
  val salesperson = new Employee("Charlie", EmployeeType("salesperson"))

  assertResult("Alice (Engineer)")(engineer.toString)
  assertResult("Bob (Manager)")(manager.toString)
  assertResult("Charlie (Salesperson)")(salesperson.toString)

  assertThrows[IllegalArgumentException] {
    new Employee("Dave", EmployeeType("invalid"))
  }
}
