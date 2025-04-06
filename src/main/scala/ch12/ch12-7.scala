package ch12_7

import org.scalatest.Assertions.*

class Person(val name: String, val genderCode: String = "X") {
  require(genderCode == "M" || genderCode == "F" || genderCode == "X")

  def isMale = genderCode == "M"
}

case class Item(gender: String, name: String)

def loadFromInput(data: List[Item]): List[Person] = {
  data.map(item => createPerson(item))
}

def createPerson(item: Item) = item.gender match {
  case "M" => new Person(item.name, "M")
  case "F" => new Person(item.name, "F")
  case _ => new Person(item.name)
}

// main
@main def main() = {
  val john = new Person("John", "M")
  val jane = new Person("Jane", "F")
  val person = new Person("Person")
  assertResult("M")(john.genderCode)
  assertResult("F")(jane.genderCode)
  assertResult("X")(person.genderCode)
  val persons = loadFromInput(List(
    Item("M", "John"),
    Item("F", "Jane"),
    Item("X", "Person")
  ))
  assertResult(john.genderCode)(persons(0).genderCode)
  assert(john.isMale)
  assertResult(jane.genderCode)(persons(1).genderCode)
  assertResult(person.genderCode)(persons(2).genderCode)
}
