package org.sangho.refac2scala
package ch08_1_2

enum AccountType {
  case PREMIUM
  case BASIC

  def isPremium = this == PREMIUM

  // 초과 인출 이자 계산
  def overdraftCharge(daysOverdrawn: Int) = {
    if (isPremium) {
      val baseCharge = 10.0
      if (daysOverdrawn <= 7) {
        baseCharge
      } else {
        baseCharge + (daysOverdrawn - 7) * 0.85
      }
    } else {
      daysOverdrawn * 1.75
    }
  }
}

class Account(daysOverdrawn: Int, accountType: AccountType) {
  // 은행 이자 계산
  def bankCharge() = {
    var result = 4.5
    if (daysOverdrawn > 0) result += overdraftCharge
    result
  }
  // 초과 인출 이자 계산
  def overdraftCharge = {
    accountType.overdraftCharge(daysOverdrawn)
  }
}

// main
@main def main(): Unit = {
  assert(Account(10, AccountType.PREMIUM).bankCharge() == 17.05)
  assert(Account(5, AccountType.PREMIUM).bankCharge() == 14.5)
  assert(Account(10, AccountType.BASIC).bankCharge() == 22.0)
}
