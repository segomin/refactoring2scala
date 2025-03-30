package org.sangho.refac2scala
package ch11_8

class Employee(val name: String, val typeCode: String) {
  def kind() = Employee.legalTypeCodes.getOrElse(typeCode, "Unknown")
}

object Employee {
  val legalTypeCodes = Map(
    "E" -> "Employee",
    "M" -> "Manager",
    "S" -> "Salesman",
  )
}

// main
@main def main(): Unit = {
  val martin = new Employee("Martin", "E")
  val bob = new Employee("Bob", "M")
  val other = new Employee("Other", "X")
  assert(martin.kind() == "Employee")
  assert(bob.kind() == "Manager")
  assert(other.kind() == "Unknown")
}
