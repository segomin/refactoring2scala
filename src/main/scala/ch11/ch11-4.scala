package org.sangho.refac2scala
package ch11

case class TemperatureRange(low: Int, high: Int) {
  def contains(temp: Int): Boolean = temp >= low && temp <= high
}

class HeatingPlan(val temperatureRange: TemperatureRange) {

  def withinRange(numberRange: TemperatureRange): Boolean = {
    numberRange.low >= temperatureRange.low && numberRange.high <= temperatureRange.high
  }
}

class Room(
    private val _temperatureRange: TemperatureRange
) {
  def daysTempRange: TemperatureRange = _temperatureRange
}

def test (room: Room, plan: HeatingPlan, alserts: String => Unit): Unit = {
  if (!plan.withinRange(room.daysTempRange)) {
    alserts.apply("방 온도가 지정 범위를 벗어났습니다.")
  }
}


// main
@main def main(): Unit = {
  val room = new Room(TemperatureRange(10, 20))
  var alertMsg = ""
  test(room, new HeatingPlan(TemperatureRange(9, 21)), msg => alertMsg = msg)
  assert(alertMsg == "")
  test(room, new HeatingPlan(TemperatureRange(11, 20)), msg => alertMsg = msg)
  assert(alertMsg == "방 온도가 지정 범위를 벗어났습니다.")
}
