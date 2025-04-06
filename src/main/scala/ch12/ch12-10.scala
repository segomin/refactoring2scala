package ch12_10

import org.scalatest.Assertions.*

import java.time.OffsetDateTime

case class Show(price: Double, talkback: String = null) {
  def hasTalkback: Boolean = {
    talkback != null
  }
}
case class Extras(premiumFee: Double, dinner: String = null) {
  def hasDinner: Boolean = {
    dinner != null
  }
}

class Booking(val show: Show, date: OffsetDateTime) {
  var premiumDelegate: PremiumBookingDelegate = null
  def hasTalkback: Boolean = {
    if (premiumDelegate != null)
      premiumDelegate.hasTalkback else
    show.hasTalkback && !isPeakDay
  }

  def isPeakDay: Boolean = {
    date.getDayOfWeek.getValue == 6 || date.getDayOfWeek.getValue == 7
  }

  def basePrice: Double = {
    if (premiumDelegate != null)
      premiumDelegate.basePrice
    else
      privateBasePrice
  }

  def privateBasePrice = {
    var result = show.price
    if (isPeakDay) {
      result += (result * 0.15).round
    }
    result
  }

  def _bePremium(extras: Extras): Unit = {
    premiumDelegate = new PremiumBookingDelegate(this, extras)
  }
}

class PremiumBooking(show: Show, date: OffsetDateTime, val extras: Extras) extends Booking(show, date) {
  def hasDinner: Boolean = {
    extras.hasDinner && isPeakDay
  }
}

class PremiumBookingDelegate(val host: Booking, val extras: Extras) {
  def basePrice: Double =
    (host.privateBasePrice + extras.premiumFee).round

  def hasTalkback: Boolean = {
    host.show.hasTalkback
  }
}

def createBooking(show: Show, date: OffsetDateTime): Booking = {
  new Booking(show, date)
}

def createPremiumBooking(show: Show, date: OffsetDateTime, extras: Extras): PremiumBooking = {
  val result = new PremiumBooking(show, date, extras)
  result._bePremium(extras)
  result
}


// main
@main def main() = {
  val show = Show(100.0, "Talkback")
  val monday = OffsetDateTime.parse("2025-04-07T10:00:00Z")
  val booking = createBooking(show, monday)
  assertResult(true)(booking.hasTalkback)
  assertResult(false)(booking.isPeakDay)
  assertResult(100.0)(booking.basePrice)

  val sunday = OffsetDateTime.parse("2025-04-06T10:00:00Z")
  val premiumBooking = createPremiumBooking(show, sunday, Extras(10, "Dinner"))
  assertResult(true)(premiumBooking.hasTalkback)
  assertResult(true)(premiumBooking.isPeakDay)
  assertResult(125.0)(premiumBooking.basePrice)
  assertResult(true)(premiumBooking.hasDinner)
}
