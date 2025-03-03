package org.sangho.refac2scala
package ch07_1_1

import scala.collection.mutable

val organization = mutable.Map("name" -> "애크미 구스베리", "country" -> "GB")

class Order(data: Map[String, String]) {
  private var _name = data("name")
  private var _country = data("country")

  def name: String = _name

  def country: String = _country

  def name_=(newName: String): Unit = _name = newName

  def country_=(newCountry: String): Unit = _country = newCountry
}

// main
@main def main(): Unit = {
  val order = new Order(organization.toMap)
  val result = s"""<h1>${order.name}</h1><p>${order.country}</p>"""
  assert(result == "<h1>애크미 구스베리</h1><p>GB</p>")
  order.name = "사과"
  assert(order.name == "사과")
}