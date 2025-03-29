package org.sangho.refac2scala
package ch11_1

trait Alarmable {
  def setOffAlarms(): Unit
}

def alertForMiscreant(people: List[String], alarmable: Alarmable): Unit = {
  for (p <- people) {
    if (p == "조커") {
      alarmable.setOffAlarms()
      return
    }
    if (p == "사루만") {
      alarmable.setOffAlarms()
      return
    }
  }
}

def findMiscreant(people: List[String]): String = {
  for (p <- people) {
    if (p == "조커") {
      return "조커"
    }
    if (p == "사루만") {
      return "사루만"
    }
  }
  ""
}

// main
@main def main(): Unit = {
  class MockAlarmable extends Alarmable {
    var hasAlert = 0
    def setOffAlarms(): Unit = {
      hasAlert += 1
    }
  }

  val people1 = List("조커", "사루만", "배트맨")
  val mockAlarm1 = new MockAlarmable
  val alert1 = findMiscreant(people1)
  alertForMiscreant(people1, mockAlarm1)
  assert(alert1 == "조커")
  assert(mockAlarm1.hasAlert == 1)

  val people2 = List("배트맨", "슈퍼맨", "알라딘")
  val mockAlarm2 = new MockAlarmable
  val alert2 = findMiscreant(people2)
  alertForMiscreant(people2, mockAlarm2)
  assert(alert2 == "")
  assert(mockAlarm2.hasAlert == 0)
}
