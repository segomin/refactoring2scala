package org.sangho.refac2scala
package ch11_2

case class Money(amount: Double, currency: String = "USD") {
  def multiply(factor: Double): Money = Money(amount * factor)
}

class Person(name: String, var salary: Money) {
  def this(name: String) = this(name, Money(0))
}

// simple case
def raise(person: Person, factor: Double): Unit = {
  person.salary = person.salary.multiply(factor)
}

// complicate case
def usd(amount: Double) = Money(amount, "USD")

def withinBand(usage: Int, bottom: Int, top: Int) = if (usage > bottom) Math.min(usage, top) - bottom else 0

def baseCharge(usage: Int): Money = {
  if (usage < 0) return usd(0)
  val amount = withinBand(usage, 0, 100) * 0.03
    + withinBand(usage, 100, 200) * 0.05
    + withinBand(usage, 200, Int.MaxValue) * 0.07
  usd(amount)
}

// main
@main def main(): Unit = {
  // simple case
  val person1 = new Person("Alice", Money(1000))
  raise(person1, 1.1)
  assert(person1.salary.amount == 1100.0)
  val person2 = new Person("Bob", Money(2000))
  raise(person2, 1.05)
  assert(person2.salary.amount == 2100.0)

  // complicate case
  assert(baseCharge(50) == usd(50 * 0.03))
  assert(baseCharge(150) == usd(100 * 0.03 + 50 * 0.05))
  assert(baseCharge(250) == usd(100 * 0.03 + 100 * 0.05 + 50 * 0.07))
}
