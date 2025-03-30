package org.sangho.refac2scala
package ch11_7

class Person {
  private var _name = ""
  private var _id = ""

  def name: String = _name
  def name_=(arg: String): Unit = _name = arg

  def id: String = _id
  def id_=(arg: String): Unit = _id = arg
}

// main
@main def main(): Unit = {
  val martin = new Person()
  martin.name = "Martin"
  martin.id = "1234"

  assert(martin.name == "Martin")
  assert(martin.id == "1234")
}
