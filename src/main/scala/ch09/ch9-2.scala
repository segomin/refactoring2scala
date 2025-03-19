package org.sangho.refac2scala
package ch09_2

val organization = Map("title" -> "애크미 구스베리", "country" -> "68")

class Organization(data: Map[String, String]) {
  private var _title = if (data.contains("title")) data("title") else data("name")
  private var _country = data("country")

  def title: String = _title
  def title_=(aString: String): Unit = _title = aString

  def country: String = _country
  def country_=(aCountryCode: String): Unit = _country = aCountryCode
}

// main
@main def main(): Unit = {
  val organization = new Organization(Map("name" -> "애크미 구스베리", "country" -> "68"))
  assert(organization.title == "애크미 구스베리")
  assert(organization.country == "68")
  organization.title = "새로운 이름"
  organization.country = "69"
  assert(organization.title == "새로운 이름")
  assert(organization.country == "69")
}
