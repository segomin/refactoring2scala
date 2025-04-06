package ch12_6

import org.scalatest.Assertions.*

class Employee(val name: String, val _kind: String) {
  require(_kind == "engineer" || _kind == "manager" || _kind == "salesperson",
    s"${_kind}라는 직원 유형은 없습니다.")
  def kind = _kind

  override def toString = s"$name ($kind)"
}

class Engineer(name: String) extends Employee(name, "engineer") {
  override val kind: String = "engineer"
}

def createEmployee(name: String, kind: String): Employee = {
  kind match {
    case "engineer" => new Engineer(name)
    case _ => new Employee(name, kind)
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
