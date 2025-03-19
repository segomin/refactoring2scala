package org.sangho.refac2scala
package ch09

class TelephoneNumber {
  private var _areaCode = ""
  private var _number = ""

  def areaCode: String = _areaCode
  def areaCode_=(arg: String): Unit = _areaCode = arg

  def number: String = _number
  def number_=(arg: String): Unit = _number = arg
}

class Person {
  private val _telephoneNumber = new TelephoneNumber()

  def officeAreaCode: String = _telephoneNumber.areaCode
  def officeAreaCode_=(arg: String): Unit = _telephoneNumber.areaCode = arg

  def officeNumber: String = _telephoneNumber.number
  def officeNumber_=(arg: String): Unit = _telephoneNumber.number = arg
}

// main
@main def main(): Unit = {
  val person = new Person()
  person.officeAreaCode = "02"
  person.officeNumber = "1234-5678"
  assert(person.officeAreaCode == "02")
  assert(person.officeNumber == "1234-5678")
}
