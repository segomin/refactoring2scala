package ch12_6

import org.scalatest.Assertions.*

abstract class Employee(val name: String) {
  def kind: String

  override def toString = s"$name ($kind)"
}

class Engineer(name: String) extends Employee(name) {
  override val kind: String = "engineer"
}
class Manager(name: String) extends Employee(name) {
  override val kind: String = "manager"
}
class Salesperson(name: String) extends Employee(name) {
  override val kind: String = "salesperson"
}

def createEmployee(name: String, kind: String): Employee = {
  kind match {
    case "engineer" => new Engineer(name)
    case "manager" => new Manager(name)
    case "salesperson" => new Salesperson(name)
    case _ => throw new IllegalArgumentException(s"${kind}라는 직원 유형은 없습니다.")
  }
}

// main
@main def main() = {
  val engineer = createEmployee("Alice", "engineer")
  val manager = createEmployee("Bob", "manager")
  val salesperson = createEmployee("Charlie", "salesperson")

  assertResult("Alice (engineer)")(engineer.toString)
  assertResult("Bob (manager)")(manager.toString)
  assertResult("Charlie (salesperson)")(salesperson.toString)

  assertThrows[IllegalArgumentException] {
    createEmployee("Dave", "invalid")
  }
}
