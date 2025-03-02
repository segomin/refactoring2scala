package org.sangho.refac2scala
package ch07_1

val organization = Map("name" -> "애크미 구스베리", "country" -> "GB")

class Order(data: Map[String, String]) {
  private var _name = data("name")
  private var _country = data("country")

  def name = _name

  def country = _country

  def name_=(newName: String): Unit = {
    _name = newName
  }

  def country_=(newCountry: String): Unit = {
    _country = newCountry
  }
}

@main def main(): Unit = {
  val order = new Order(organization)
  assert(order.name == "애크미 구스베리")
  assert(order.country == "GB")

  order.name = "사과"
  order.country = "KO"
  assert(order.name == "사과")
  assert(order.country == "KO")
}