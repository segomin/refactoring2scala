package org.sangho.refac2scala
package ch11_6

class Thermostat(var _currentTemperature: Double = 0.0, var _selectedTemperature: Double = 0.0) {
  def setCurrentTemperature(temperature: Double): Unit = {
    _currentTemperature = temperature
  }
  def setSelectTemperature(temperature: Double): Unit = {
    _selectedTemperature = temperature
  }
  def currentTemperature: Double = _currentTemperature
  def selectedTemperature: Double = _selectedTemperature
}

class HeatingPlan(val _min: Double, val _max: Double) {

  def targetTemperature(selectedTemperature: Double) = {
    if (selectedTemperature > this._max) this._max
    else if (selectedTemperature < this._min) this._min
    else selectedTemperature
  }
}

class Room {
  var _heatState: String = "off"
  def setTemperature(thePlan: HeatingPlan, thermostat: Thermostat): Unit = {
    if (thePlan.targetTemperature(thermostat.selectedTemperature) > thermostat.currentTemperature) {
      setToHeat()
    } else if (thePlan.targetTemperature(thermostat.selectedTemperature) < thermostat.currentTemperature) {
      setToCool()
    } else {
      setOff()
    }
  }
  private def setToHeat(): Unit = {
    _heatState = "heat"
  }
  private def setToCool(): Unit = {
    _heatState = "cool"
  }
  private def setOff(): Unit = {
    _heatState = "off"
  }
  def heatState: String = _heatState
}


// main
@main def main(): Unit = {
  val room = new Room()
  room.setTemperature(HeatingPlan(10, 20), Thermostat(15, 23))
  assert(room.heatState == "heat")
  room.setTemperature(HeatingPlan(10, 20), Thermostat(23, 15))
  assert(room.heatState == "cool")
  room.setTemperature(HeatingPlan(10, 20), Thermostat(10, 9))
  assert(room.heatState == "off")
  room.setTemperature(HeatingPlan(10, 20), Thermostat(15, 15))
  assert(room.heatState == "off")
  room.setTemperature(HeatingPlan(10, 20), Thermostat(20, 25))
  assert(room.heatState == "off")
}
