package org.sangho.refac2scala
package ch07_2

import scala.collection.mutable

class Course (val name: String, val isAdvanced: Boolean)

class Person (val name: String) {
  private var _courses: mutable.Set[Course] = mutable.Set()

  def courses: mutable.Set[Course] = _courses
  def courses_=(list: mutable.Set[Course]): Unit = _courses = list
}

@main def main(): Unit = {
  val kent = new Person("마틴")
  kent.courses = mutable.Set(new Course("리펙토링", false), new Course("리펙토링2nd", true))
  val numAdvancedCourses = kent.courses.count(course => course.isAdvanced)
  assert(numAdvancedCourses == 1)
}