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

def createEngineer(name: String): Employee = {
  new Employee(name, "E")
}
def createManager(name: String): Employee = {
  new Employee(name, "M")
}
def createSalesman(name: String): Employee = {
  new Employee(name, "S")
}

// main
@main def main(): Unit = {
  val martin = createEngineer("Martin")
  val bob = createManager("Bob")
  val other = createSalesman("Other")
  assert(martin.kind() == "Employee")
  assert(bob.kind() == "Manager")
  assert(other.kind() == "Salesman")
}
