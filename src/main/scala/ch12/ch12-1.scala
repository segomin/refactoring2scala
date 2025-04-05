package ch12_1

import org.scalatest.Assertions._

trait Party() {
  def monthlyCost: Int
}

case class Employee(monthlyCost: Int) extends Party {
  def annualCost(): Int = {
    this.monthlyCost * 12
  }
}
case class Department(monthlyCost: Int) extends Party {
  def totalAnnualCost(): Int = {
    this.monthlyCost * 12
  }
}

// main
@main def main() = {
  val employee = Employee(1000)
  val department = Department(2000)

  assert(employee.annualCost() == 12000)
  assert(department.totalAnnualCost() == 24000)
}
