package org.sangho.refac2scala
package ch10_7

def sendVillainAlert(people: List[String], sendAlertRun: Runnable): Unit = {
  def sendAlert(): Unit = {
    sendAlertRun.run()
  }

  def checkForMiscreants(people: List[String]): Unit = {
    for (p <- people) {
      if (p == "조커") {
        sendAlert()
        return
      }
      if (p == "사루만") {
        sendAlert()
        return
      }
    }
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
