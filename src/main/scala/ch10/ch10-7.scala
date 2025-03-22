package org.sangho.refac2scala
package ch10_7

def sendVillainAlert(people: List[String], sendAlert: Runnable): Unit = {
  var found = false
  for (p <- people) {
    if (!found) {
      if (p == "조커") {
        sendAlert.run()
        found = true
      }
      if (p == "사루만") {
        sendAlert.run()
        found = true
      }
    }
  }
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
