package org.sangho.refac2scala
package ch08

case class AccountType(name: String)

class Account(val number: String, val aType: AccountType, val interestRate: Double) {
}

// main
@main def main(): Unit = {
  val accountType = AccountType("예금")
  val account = new Account("1234", accountType, 0.03)
  println(account)
  assert(account.number == "1234")
  assert(account.aType == accountType)
  assert(account.interestRate == 0.03)
}
