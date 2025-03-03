package org.sangho.refac2scala
package ch07

class Shipment(__shippingCompany: String, __trackingNumber: String) {
  private var _shippingCompany = __shippingCompany
  private var _trackingNumber = __trackingNumber
  def trackingInfo: String = s"${_shippingCompany}: ${_trackingNumber}"
  def shippingCompany(): String = _shippingCompany
  def shippingCompany_=(arg: String): Unit = _shippingCompany = arg
}


@main def main(): Unit = {
  // test
  val shipment = new Shipment("우체국", "1234")
  assert(shipment.trackingInfo == "우체국: 1234")
  shipment.shippingCompany = "대한통운"
  assert(shipment.trackingInfo == "대한통운: 1234")
}