package org.sangho.refac2scala
package ch11_7

class Person (val id: String){
  private var _name = ""

  def name: String = _name
  def name_=(arg: String): Unit = _name = arg
}

// main
@main def main(): Unit = {
  val martin = new Person("1234")
  martin.name = "Martin"

  assert(martin.name == "Martin")
  assert(martin.id == "1234")
}
