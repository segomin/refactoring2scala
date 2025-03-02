package org.sangho.refac2scala
package ch07_1_2

import scala.collection.mutable

val nestedRecord = mutable.Map(
  "1920" -> mutable.Map("name" -> "마틴 파울러", "id" -> "1920",
    "usages" -> mutable.Map(
      "2016" -> mutable.Map("1" -> 50, "2" -> 55),
      "2015" -> mutable.Map("1" -> 70, "2" -> 63)))
)

@main def main(): Unit = {
  // 쓰기예
  nestedRecord("1920")("usages").asInstanceOf[mutable.Map[String, mutable.Map[String, Int]]]("2015")("1") = 80
  val result =nestedRecord("1920")("usages").asInstanceOf[mutable.Map[String, mutable.Map[String, Int]]]("2015")("1")
  assert(result == 80)

  // 읽기예
  def compareUsage(customerID: String, lastYear: String, month: String): Int = {
    val amount1 = nestedRecord(customerID)("usages").asInstanceOf[mutable.Map[String, mutable.Map[String, Int]]](lastYear)(month)
    val amount2 = nestedRecord(customerID)("usages").asInstanceOf[mutable.Map[String, mutable.Map[String, Int]]]((lastYear.toInt - 1).toString)(month)
    amount1 - amount2
  }
  assert(compareUsage("1920", "2016", "1") == -30)
}