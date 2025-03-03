package org.sangho.refac2scala
package ch07

class TrackingInformation (__shippingCompany: String, __trackingNumber: String) {
  private var _shippingCompany = __shippingCompany
  private var _trackingNumber = __trackingNumber

  def shippingCompany: String = _shippingCompany
  def trackingNumber: String = _trackingNumber

  def shippingCompany_=(arg: String): Unit = _shippingCompany = arg
  def trackingNumber_=(arg: String): Unit = _trackingNumber = arg

  def display: String = s"${shippingCompany}: ${trackingNumber}"
}

class Shipment(private var _trackingInformation: TrackingInformation) {
  def trackingInfo: String = _trackingInformation.display
  def trackingInformation: TrackingInformation = _trackingInformation
  def trackingInformation_=(arg: TrackingInformation): Unit = _trackingInformation = arg
}


@main def main(): Unit = {
  // test
  val trackingInformation = new TrackingInformation("우체국", "1234")
  val shipment = new Shipment(trackingInformation)
  assert(shipment.trackingInfo == "우체국: 1234")
  assert(shipment.trackingInformation == trackingInformation)
}