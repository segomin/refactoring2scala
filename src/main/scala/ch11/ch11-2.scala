package org.sangho.refac2scala
package ch11_2

case class Money(amount: Double, currency: String = "USD") {
  def multiply(factor: Double): Money = Money(amount * factor)
}

class Person(name: String, var salary: Money) {
  def this(name: String) = this(name, Money(0))
}

// simple case

def tenPercentRaise(person: Person): Unit = {
  person.salary = person.salary.multiply(1.1)
}

def fivePercentRaise(person: Person): Unit = {
  person.salary = person.salary.multiply(1.05)
}

// complicate case
def usd(amount: Double) = Money(amount, "USD")

def bottomBand(usage: Int) = Math.min(usage, 100)
def middleBand(usage: Int) = if (usage > 100) Math.min(usage, 200) - 100 else 0
def topBand(usage: Int) = if (usage > 200) usage - 200 else 0

def baseCharge(usage: Int): Money = {
  if (usage < 0) return usd(0)
  val amount = bottomBand(usage) * 0.03 + middleBand(usage) * 0.05 + topBand(usage) * 0.07
  usd(amount)
}

// main
@main def main(): Unit = {
  // simple case
  val person1 = new Person("Alice", Money(1000))
  tenPercentRaise(person1)
  assert(person1.salary.amount == 1100.0)
  val person2 = new Person("Bob", Money(2000))
  fivePercentRaise(person2)
  assert(person2.salary.amount == 2100.0)

  // complicate case
  assert(baseCharge(50) == usd(50 * 0.03))
  assert(baseCharge(150) == usd(100 * 0.03 + 50 * 0.05))
  assert(baseCharge(250) == usd(100 * 0.03 + 100 * 0.05 + 50 * 0.07))
}
