package org.sangho.refac2scala
package ch07_9

def foundPerson(people: List[String]): String = {
  for (person <- people) {
    if (person == "Don") return "Don"
    if (person == "John") return "John"
    if (person == "Kent") return "Kent"
  }
  ""
}


@main def main(): Unit = {
  // test
  val people = List("Done", "John", "Kent")
  val found = foundPerson(people)
  assert(found == "John")
}