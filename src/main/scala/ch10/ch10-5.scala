package org.sangho.refac2scala
package ch10_5

class Customer(__name: String, __paymentHistory: PaymentHistory = PaymentHistory(0)) {
  private val _name: String = __name
  private var _billingPlan: String = "normal"
  private var _paymentHistory: PaymentHistory = __paymentHistory

  def name: String = _name

  def billingPlan: String = _billingPlan

  def billingPlan_=(arg: String): Unit = _billingPlan = arg

  def paymentHistory: PaymentHistory = _paymentHistory

  def paymentHistory_=(arg: PaymentHistory): Unit = _paymentHistory = arg

  def isKindOf(arg: String): Boolean = arg == name

  def isUnknown: Boolean = false
}

class UnknownCustomer(paymentHistory: PaymentHistory) extends Customer("미확인 고객", paymentHistory) {
  override def name: String = "거주자"

  override def isUnknown: Boolean = true

  override def billingPlan_=(arg: String): Unit = {}
}

case class PaymentHistory(weeksDelinquentInLastYear: Int)

class Site(__customer: Customer) {
  private var _customer: Customer = if (__customer.isKindOf("미확인 고객")) {
    new UnknownCustomer(__customer.paymentHistory)
  }
  else {
    __customer
  }

  def customer: Customer = _customer

  def customer_=(arg: Customer): Unit = _customer = arg
}

case class BillingPlans(basic: String = "basic")

case class Registry(billingPlans: BillingPlans)

def client1(site: Site): String = {
  val customer = site.customer
  customer.name
}

def client2(site: Site): String = {
  val registry = Registry(BillingPlans())
  val customer = site.customer
  val plan = if (customer.isUnknown) registry.billingPlans.basic else customer.billingPlan
  plan
}

def client3(site: Site, newPlan: String): Unit = {
  val customer = site.customer
  customer.billingPlan = newPlan
}

def client4(site: Site): Int = {
  val customer = site.customer
  val weeksDelinquent = if (customer.isUnknown) {
    0
  } else {
    customer.paymentHistory.weeksDelinquentInLastYear
  }
  weeksDelinquent
}

// main
@main def main(): Unit = {
  val site = Site(Customer("홍길동", PaymentHistory(1)))
  assert(client1(site) == "홍길동")
  assert(client2(site) == "normal")
  client3(site, "new plan")
  assert(site.customer.billingPlan == "new plan")
  assert(client4(site) == 1)

  val unknownSite = Site(Customer("미확인 고객", PaymentHistory(1)))
  assert(client1(unknownSite) == "거주자")
  assert(client2(unknownSite) == "basic")
  client3(unknownSite, "new plan")
  assert(unknownSite.customer.billingPlan == "normal")
  assert(client4(unknownSite) == 0)
}
