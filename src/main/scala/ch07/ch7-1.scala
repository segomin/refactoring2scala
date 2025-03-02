package org.sangho.refac2scala
package ch07_1

import scala.collection.mutable

val organization = mutable.Map("name" -> "애크미 구스베리", "country" -> "GB")

@main def main(): Unit = {
  val result = s"""<h1>${organization("name")}</h1><p>${organization("country")}</p>"""
  assert(result == "<h1>애크미 구스베리</h1><p>GB</p>")
  organization("name") = "사과"
  assert(organization("name") == "사과")
}