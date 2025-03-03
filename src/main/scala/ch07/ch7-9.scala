package org.sangho.refac2scala
package ch07_9

def foundPerson(people: List[String]): String = {
  people.find(Set("Don", "John", "Kent").contains).getOrElse("")
}


@main def main(): Unit = {
  // test
  val people = List("Done", "John", "Kent")
  val found = foundPerson(people)
  assert(found == "John")
}