package org.sangho.refac2scala
package ch12_3

import org.scalatest.Assertions._


abstract class Party (val name: String) {}
class Employee(name: String, val id: String, val monthlyCost: Int) extends Party(name) {
  def isPrivileged: Boolean = {
    this.monthlyCost > 1000
  }
  def assignCar() = {
    println(s"Assigning car to $name")
  }
  def finishConstructor(): Unit = {
    if (isPrivileged) {
      assignCar()
    }
  }
}
class Department(name: String, val monthlyCost: Int) extends Party(name) {}

class Manager(name: String, id: String, monthlyCost: Int) extends Employee(name, id, monthlyCost) {
  private var _grade: Int = 0
  def this(name: String, grade: Int, id: String, monthlyCost: Int) = {
    this(name, id, monthlyCost)
    this._grade = grade
    finishConstructor()
  }
}

// main
@main def main() = {
  val employee = new Employee("John Doe", "E123", 1000)
  assertResult("John Doe")(employee.name)
  assertResult("E123")(employee.id)
  assertResult(1000)(employee.monthlyCost)
  val department = new Department("HR", 2000)
  assertResult("HR")(department.name)
  assertResult(2000)(department.monthlyCost)
  assertResult(24000)(department.monthlyCost * 12)

  val manager = new Manager("Jane Doe", 1, "M123", 1500)
  assertResult("Jane Doe")(manager.name)
  assertResult(true)(manager.isPrivileged)
}
