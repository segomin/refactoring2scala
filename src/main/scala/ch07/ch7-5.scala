package org.sangho.refac2scala
package ch07_5

class Person(val name: String, _officeAreaCode: String, _officeNumber: String) {
  private var _telephoneNumber = TelephoneNumber(_officeAreaCode, _officeNumber)

  def officeAreaCode: String = _telephoneNumber.areaCode

  def officeAreaCode_=(arg: String): Unit = _telephoneNumber = _telephoneNumber.copy(areaCode = arg)

  def officeNumber: String = _telephoneNumber.number

  def officeNumber_=(arg: String): Unit = _telephoneNumber = _telephoneNumber.copy(number = arg)

  def telephoneNumber: String = _telephoneNumber.telephoneNumber
}

case class TelephoneNumber(areaCode: String, number: String) {
  def telephoneNumber: String = s"(${areaCode}) ${number}"
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