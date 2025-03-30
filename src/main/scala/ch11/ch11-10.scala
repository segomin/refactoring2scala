package org.sangho.refac2scala
package ch11_10

case class Customer(baseRate: Double)

case class Provider(connectionCharge: Double)

class ChargeCalculator {
  def charge(customer: Customer, usage: Int, provider: Provider): Double = {
    val baseCharge = customer.baseRate * usage
    baseCharge + provider.connectionCharge
  }
}

def charge(customer: Customer, usage: Int, provider: Provider): Double = {
  new ChargeCalculator().charge(customer, usage, provider)
}

// main
@main def main() = {
  assert(charge(Customer(0.03), 100, Provider(0.1)) == 3.1)
  assert(charge(Customer(0.05), 200, Provider(0.2)) == 10.2)
}
