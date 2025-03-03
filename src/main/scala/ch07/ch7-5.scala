package org.sangho.refac2scala
package ch07_5

class Person(val name: String, var officeAreaCode: String, var officeNumber: String) {
  def telephoneNumber: String = s"(${officeAreaCode}) ${officeNumber}"
}

@main def main(): Unit = {
  // getter
  val person = new Person("마틴", "010", "1234")
  assert(person.name == "마틴")
  assert(person.officeAreaCode == "010")
  assert(person.officeNumber == "1234")
  assert(person.telephoneNumber == "(010) 1234")
  // setter
  person.officeAreaCode = "011"
  person.officeNumber = "5678"
  assert(person.officeAreaCode == "011")
  assert(person.officeNumber == "5678")
  assert(person.telephoneNumber == "(011) 5678")
}