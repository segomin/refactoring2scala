package ch12_7

import org.scalatest.Assertions.*

class Person(val name: String) {
  def genderCode = "X"
}
class Male(name: String) extends Person(name) {
  override def genderCode = "M"
}
class Female(name: String) extends Person(name) {
  override def genderCode = "F"
}

case class Item(gender: String, name: String)

def loadFromInput(data: List[Item]): List[Person] = {
  data.map(item => item.gender match {
    case "M" => new Male(item.name)
    case "F" => new Female(item.name)
    case _ => new Person(item.name)
  })
}

// main
@main def main() = {
  val john = new Male("John")
  val jane = new Female("Jane")
  assertResult("M")(john.genderCode)
  assertResult("F")(jane.genderCode)
  val persons = loadFromInput(List(
    Item("M", "John"),
    Item("F", "Jane")
  ))
  assertResult(john.genderCode)(persons(0).genderCode)
  assertResult(jane.genderCode)(persons(1).genderCode)
}
