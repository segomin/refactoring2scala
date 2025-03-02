package org.sangho.refac2scala
package ch07_1

import scala.collection.mutable

val organization = mutable.Map("name" -> "애크미 구스베리", "country" -> "GB")

class Order(data: mutable.Map[String, String]) {
  private var _data = data

  def name = _data("name")

  def country = _data("country")

  def name_=(newName: String): Unit = {
    _data("name") = newName
  }

  def country_=(newCountry: String): Unit = {
    _data("country") = newCountry
  }
}

// main
@main def main(): Unit = {
  val order = new Order(organization)
  val result = s"""<h1>${order.name}</h1><p>${order.country}</p>"""
  assert(result == "<h1>애크미 구스베리</h1><p>GB</p>")
  order.name = "사과"
  assert(order.name == "사과")
}