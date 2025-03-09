package org.sangho.refac2scala
package ch08

case class AccountType(name: String, interestRate: Double)

class Account(val number: String, val aType: AccountType, _interestRate: Double) {
  require(_interestRate == aType.interestRate, "이자율이 일치하지 않습니다.")

  def interestRate: Double = aType.interestRate
}

// main
@main def main(): Unit = {
  val accountType = AccountType("예금", 0.03)
  val account = new Account("1234", accountType, 0.03)
  println(account)
  assert(account.number == "1234")
  assert(account.aType == accountType)
  assert(account.interestRate == 0.03)

  // check throw exception
  try {
    val _ = new Account("1234", accountType, 0.04)
    assert(false, "이 코드는 실행되지 않아야 함")
  }
  catch {
    case e: IllegalArgumentException => println(e)
  }
}
