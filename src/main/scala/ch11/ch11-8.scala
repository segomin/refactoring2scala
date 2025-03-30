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

def createEmployee(name: String, typeCode: String): Employee = {
  new Employee(name, typeCode)
}

// main
@main def main(): Unit = {
  val martin = createEmployee("Martin", "E")
  val bob = createEmployee("Bob", "M")
  val other = createEmployee("Other", "X")
  assert(martin.kind() == "Employee")
  assert(bob.kind() == "Manager")
  assert(other.kind() == "Unknown")
}
