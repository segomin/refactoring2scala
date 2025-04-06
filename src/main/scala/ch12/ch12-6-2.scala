package ch12_6_2

import org.scalatest.Assertions.*

trait EmployeeType {
  def value: String
  override def toString: String = value
}
case class Engineer() extends EmployeeType {
  override def value: String = "engineer"
}
case class Manager() extends EmployeeType {
  override def value: String = "manager"
}
case class Salesperson() extends EmployeeType {
  override def value: String = "salesperson"
}

class Employee(val name: String, _kind: String) {
  val kind = Employee.createEmployeeType(_kind)

  def capitalizedKind: String = {
    kind.value.capitalize
  }

  override def toString = s"$name ($capitalizedKind)"
}

object Employee {
  def createEmployeeType(kind: String): EmployeeType = {
    kind match {
      case "engineer" => Engineer()
      case "manager" => Manager()
      case "salesperson" => Salesperson()
      case _ => throw new IllegalArgumentException(s"${kind}라는 직원 유형은 없습니다.")
    }
  }
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
