package ch12_11

import org.scalatest.Assertions.*

import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit


class CatalogItem(val id: String, val title: String, val tags: List[String]) {
  def hasTag(tag: String): Boolean = tags.contains(tag)
}

class Scroll(_id: String, val catalogItem: CatalogItem, val lastCleaned: OffsetDateTime) {
  def id: String = catalogItem.id
  def title: String = catalogItem.title
  def hasTag(tag: String): Boolean = catalogItem.hasTag(tag)

  def needCleaning(targetDate: OffsetDateTime): Boolean = {
    val threshold = if hasTag("revered") then 700 else 1500
    daySinceLastCleaning(targetDate) > threshold
  }

  def daySinceLastCleaning(targetDate: OffsetDateTime): Int = {
    lastCleaned.until(targetDate, ChronoUnit.DAYS).toInt
  }
}

// main
@main def main() = {
  val catalogItem = new CatalogItem("1", "The Book of Shadows", List("magic", "revered"))
  val scroll = new Scroll("1", catalogItem, OffsetDateTime.now().minusDays(800))
  assertResult(800)(scroll.daySinceLastCleaning(OffsetDateTime.now()))
  assertResult(true)(scroll.needCleaning(OffsetDateTime.now()))
  assertResult(false)(scroll.needCleaning(OffsetDateTime.now().minusDays(600)))
  assertResult(true)(scroll.hasTag("magic"))
  assertResult(false)(scroll.hasTag("none"))
}
