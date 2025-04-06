package ch12_11

import org.scalatest.Assertions.*

import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit


class Scroll(_id: String, _title: String, _tags: List[String], val lastCleaned: OffsetDateTime) {
  val _catalogItem = new CatalogItem(_id, _title, _tags)

  def id: String = _catalogItem.id
  def title: String = _catalogItem.title
  def hasTag(tag: String): Boolean = _catalogItem.hasTag(tag)

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
  val scroll = new Scroll("1", "The Book of Shadows", List("magic", "revered"), OffsetDateTime.now().minusDays(800))
  assertResult(800)(scroll.daySinceLastCleaning(OffsetDateTime.now()))
  assertResult(true)(scroll.needCleaning(OffsetDateTime.now()))
  assertResult(false)(scroll.needCleaning(OffsetDateTime.now().minusDays(600)))
  assertResult(true)(scroll.hasTag("magic"))
  assertResult(false)(scroll.hasTag("none"))
}
