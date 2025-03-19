package org.sangho.refac2scala
package ch09_2

val organization = Map("name" -> "애크미 구스베리", "country" -> "68")

class Organization(data: Map[String, String]) {
  private var _name = data("name")
  private var _country = data("country")

  def name: String = _name
  def name_=(aString: String): Unit = _name = aString

  def country: String = _country
  def country_=(aCountryCode: String): Unit = _country = aCountryCode
}

// main
@main def main(): Unit = {
  val organization = new Organization(Map("name" -> "애크미 구스베리", "country" -> "68"))
  assert(organization.name == "애크미 구스베리")
  assert(organization.country == "68")
  organization.name = "새로운 이름"
  organization.country = "69"
  assert(organization.name == "새로운 이름")
  assert(organization.country == "69")
}
