package ch12_1

import org.scalatest.Assertions._

trait Party() {
  def monthlyCost: Int

  def annualCost(): Int = {
    this.monthlyCost * 12
  }
}

case class Employee(monthlyCost: Int) extends Party {
}
case class Department(monthlyCost: Int) extends Party {
}

// main
@main def main() = {
  val employee = Employee(1000)
  val department = Department(2000)

  assert(employee.annualCost() == 12000)
  assert(department.annualCost() == 24000)
}
