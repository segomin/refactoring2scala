package org.sangho.refac2scala
package ch09_4

case class TelephoneNumber(areaCode: String = "", number: String = "")

class Person {
  private var _telephoneNumber = TelephoneNumber()

  def officeAreaCode: String = _telephoneNumber.areaCode
  def officeAreaCode_=(arg: String): Unit = _telephoneNumber = TelephoneNumber(arg, _telephoneNumber.number)

  def officeNumber: String = _telephoneNumber.number
  def officeNumber_=(arg: String): Unit = _telephoneNumber = TelephoneNumber(_telephoneNumber.areaCode, arg)
}

// main
@main def main(): Unit = {
  val person = new Person()
  person.officeAreaCode = "02"
  person.officeNumber = "1234-5678"
  assert(person.officeAreaCode == "02")
  assert(person.officeNumber == "1234-5678")
  assert(TelephoneNumber("02", "1234-5678") == TelephoneNumber("02", "1234-5678"))
}
