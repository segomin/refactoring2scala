package org.sangho.refac2scala
package ch07

class Person(val name: String, _officeAreaCode: String, _officeNumber: String) {
  private val _telephoneNumber = TelephoneNumber(_officeAreaCode, _officeNumber)

  def officeAreaCode: String = _telephoneNumber.areaCode

  def officeNumber: String = _telephoneNumber.number
}

case class TelephoneNumber(areaCode: String, number: String) {
}


@main def main(): Unit = {
  // getter
  val person = new Person("마틴", "010", "1234")
  assert(person.name == "마틴")
  assert(person.officeAreaCode == "010")
  assert(person.officeNumber == "1234")
}