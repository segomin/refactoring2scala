package org.sangho.refac2scala
package ch07_7

class Department(private var _chargeCode: String, private var _manager: String) {
  def chargeCode: String = _chargeCode

  def chargeCode_=(arg: String): Unit = _chargeCode = arg

  def manager: String = _manager

  def manager_=(arg: String): Unit = _manager = arg
}

class Person(val name: String, private var _department: Department) {
  def department: Department = _department

  def department_=(arg: Department): Unit = _department = arg
}

@main def main(): Unit = {
  // test
  val department = new Department("1234", "마틴")
  val person = new Person("파울러", department)
  val manager = person.department.manager
  assert(manager == "마틴")
}