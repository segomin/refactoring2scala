package org.sangho.refac2scala
package ch11_13

import org.scalatest.Assertions._

case class Resource(id: String)

class ResourcePool(init: Int = 3) {
  private val available = scala.collection.mutable.Queue[Resource]()
  private val allocated = scala.collection.mutable.ListBuffer[Resource]()

  for (i <- 0 until init) {
    available.enqueue(Resource(s"Resource-$i"))
  }

  def get(): Resource = {
    val result = if available.isEmpty then Resource(s"Additional-${allocated.size + 1}") else available.dequeue()
    allocated.append(result)
    result
  }
}

// main
@main def main() = {
  val pool = new ResourcePool(2)
  val r1 = pool.get()
  val r2 = pool.get()
  val r3 = pool.get()
  assert(r1.id == "Resource-0")
  assert(r2.id == "Resource-1")
  assert(r3.id == "Additional-3")
}
