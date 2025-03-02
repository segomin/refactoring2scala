package org.sangho.refac2scala
package ch07_2

import scala.collection.mutable

case class Course (name: String, isAdvanced: Boolean)

class Person (val name: String) {
  private val _courses: mutable.Set[Course] = mutable.Set()

  def courses: mutable.Set[Course] = _courses

  def addCourse(course: Course): Unit = _courses.add(course)
  def removeCourse(course: Course): Unit = _courses.remove(course)
}

@main def main(): Unit = {
  val kent = new Person("마틴")
  kent.addCourse(Course("리펙토링", false))
  kent.addCourse(Course("리펙토링2nd", true))
  val numAdvancedCourses = kent.courses.count(course => course.isAdvanced)
  assert(numAdvancedCourses == 1)

  kent.removeCourse(Course("리펙토링2nd", true))
  assert(kent.courses.size == 1)
  assert(kent.courses.count(course => course.isAdvanced) == 0)
}