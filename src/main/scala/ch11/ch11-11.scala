package org.sangho.refac2scala
package ch11_11

case class Point(x: Double, y: Double, elevation: Double, elapse: Double)

def calculatePace(points: List[Point]): Double = {
  def calculateAscent(): Double = {
    var result: Double = 0
    for(i <- 1 until points.length) {
      val verticalChange = points(i).elevation - points(i - 1).elevation
      result += verticalChange
    }
    result
  }

  def calculateTime(): Double = {
    var totalTime: Double = 0
    for (i <- 1 until points.length) {
      val timeChange = points(i).elapse - points(i - 1).elapse
      totalTime += timeChange
    }
    totalTime
  }

  def calculateDistance(): Double = {
    var result: Double = 0
    for(i <- 1 until points.length) {
      val distanceChange = Math.sqrt(Math.pow(points(i).x - points(i - 1).x, 2) + Math.pow(points(i).y - points(i - 1).y, 2))
      result += distanceChange
    }
    result
  }

  val totalAscent = calculateAscent()
  val totalTime = calculateTime()
  val totalDistance = calculateDistance()
  val pace = totalTime / 60 / totalDistance
  // 소수점 3까지
  Math.round(pace * 1000.0) / 1000.0
}

// main
@main def main() = {
  val points = List(Point(0, 0, 0, 0), Point(20, 21, 1, 30_000), Point(40, 25, 2, 25_000), Point(15, 15, 3, 20_000), Point(0, 0, 4, 10_000))
  assert(calculatePace(points) == 1.709)
}
