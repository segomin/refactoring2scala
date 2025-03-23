package org.sangho.refac2scala
package ch10_7

def sendVillainAlert(people: List[String], sendAlertRun: Runnable): Unit = {
  def sendAlert(): Unit = {
    sendAlertRun.run()
  }

  def checkForMiscreants(people: List[String]): Unit = {
    if people.exists(p => List("조커","사루만").contains(p)) then sendAlert()
  }

  checkForMiscreants(people)
}

// main
@main def main(): Unit = {
  var shouldAlert = false
  sendVillainAlert(List("조커", "사루만", "배트맨"), () => shouldAlert = true)
  assert(shouldAlert)
  var shouldNotAlert = false
  sendVillainAlert(List("배트맨", "슈퍼맨", "알라딘"), () => shouldNotAlert = true)
  assert(!shouldNotAlert)
}
