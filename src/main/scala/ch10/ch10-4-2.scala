package org.sangho.refac2scala
package ch10_4_2

case class Voyage(zone: String, length: Int)

case class History(zone: String, profit: Int)

class Rating(val voyage: Voyage, val history: List[History]) {
  def value(): String = {
    val pf = voyageProfitFactor()
    val vr = voyageRisk()
    val chr = captainHistoryRisk()
    if (pf * 3 > (vr + chr * 2)) "A" else "B"
  }

  def voyageRisk(): Int = {
    var result = 1
    if (voyage.length > 4) result += 2
    if (voyage.length > 8) result += voyage.length - 8
    if (List("중국", "동인도").contains(voyage.zone)) result += 4
    Math.max(result, 0)
  }

  def captainHistoryRisk(): Int = {
    var result = 1
    if (history.length < 5) result += 4
    result += history.count(_.profit < 0)
    Math.max(result, 0)
  }

  def hasChinaHistory(): Boolean = {
    history.exists(_.zone == "중국")
  }

  def voyageProfitFactor(): Int = {
    var result = 2
    if (voyage.zone == "중국") result += 1
    if (voyage.zone == "동인도") result += 1
    result += voyageAndHistoryLengthFactor()
    result
  }

  private def voyageAndHistoryLengthFactor(): Int = {
    var result: Int = 0
    if (voyage.zone == "중국" && hasChinaHistory()) {
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
}

class ExperiencedChinaRating(voyage: Voyage, history: List[History]) extends Rating(voyage, history) {
  override def captainHistoryRisk(): Int = {
    val result = super.captainHistoryRisk() - 2
    Math.max(result, 0)
  }


}

def createRating(voyage: Voyage, history: List[History]) = {
  if (voyage.zone == "중국" && history.exists(_.zone == "중국"))
    new ExperiencedChinaRating(voyage, history)
  else
    new Rating(voyage, history)
}

// main
@main def main(): Unit = {
  val voyageChina = Voyage("중국", 13)
  val history = List(History("동인도", 5), History("서인도", 15), History("중국", -2), History("서아프리카", 7))
  val racingChina = createRating(voyageChina, history)
  assert(racingChina.voyageProfitFactor() == 7)
  assert(racingChina.voyageRisk() == 12)
  assert(racingChina.captainHistoryRisk() == 4)
  assert(racingChina.value() == "A")

  val voyageIndia = Voyage("서인도", 10)
  val racingIndia = createRating(voyageIndia, history)

  assert(racingIndia.voyageProfitFactor() == 2)
  assert(racingIndia.voyageRisk() == 5)
  assert(racingIndia.captainHistoryRisk() == 6)
  assert(racingIndia.value() == "B")
}
