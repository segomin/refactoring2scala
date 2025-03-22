package org.sangho.refac2scala
package ch10_4_2

case class Voyage(zone: String, length: Int)
case class History(zone: String, profit: Int)

def rating(voyage: Voyage, history: List[History]): String = {
  val pf = voyageProfitFactor(voyage, history)
  val vr = voyageRisk(voyage)
  val chr = captainHistoryRisk(voyage, history)
  if (pf * 3 > (vr + chr * 2)) "A" else "B"
}

def voyageRisk(voyage: Voyage): Int = {
  var result = 1
  if (voyage.length > 4) result += 2
  if (voyage.length > 8) result += voyage.length - 8
  if (List("중국", "동인도").contains(voyage.zone)) result += 4
  Math.max(result, 0)
}

def captainHistoryRisk(voyage: Voyage, history: List[History]): Int = {
  var result = 1
  if (history.length < 5) result += 4
  result += history.count(_.profit < 0)
  if (voyage.zone == "중국" && hasChina(history)) result -= 2
  Math.max(result, 0)
}

def hasChina(history: List[History]): Boolean = {
  history.exists(_.zone == "중국")
}

def voyageProfitFactor(voyage: Voyage, history: List[History]): Int = {
  var result = 2
  if (voyage.zone == "중국") result += 1
  if (voyage.zone == "동인도") result += 1
  if (voyage.zone == "중국" && hasChina(history)) {
    result += 3
    if (history.length > 10) result += 1
    if (voyage.length > 12) result += 1
    if (voyage.length > 18) result -= 1
  }
  else {
    if (history.length > 8) result += 1
    if (voyage.length > 14) result -= 1
  }
  result
}

// main
@main def main(): Unit = {
  val voyageChina = Voyage("중국", 13)
  val history = List(History("동인도", 5), History("서인도", 15), History("중국", -2), History("서아프리카", 7))
  assert(voyageProfitFactor(voyageChina, history) == 7)
  assert(voyageRisk(voyageChina) == 12)
  assert(captainHistoryRisk(voyageChina, history) == 4)
  assert(rating(voyageChina, history) == "A")

  val voyageIndia = Voyage("서인도", 10)
  assert(voyageProfitFactor(voyageIndia, history) == 2)
  assert(voyageRisk(voyageIndia) == 5)
  assert(captainHistoryRisk(voyageIndia, history) == 6)
  assert(rating(voyageIndia, history) == "B")
}
