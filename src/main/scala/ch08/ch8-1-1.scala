package org.sangho.refac2scala
package ch08_1_1

case class Point(lat: Double, lon: Double, elevation: Double)

case class Record(time: Double, distance: Double, pace: Double)

// GPS 추적 기록의 총 거리를 계산하는 함수
def trackSummary(points: List[Point]): Record = {
  // 시간 계산
  def calculateTime(): Double = points.foldLeft(0.0)((acc, p) => acc + p.elevation)

  def distance(p1: Point, p2: Point): Double = {
    val EARTH_RADIUS = 3959.0 // mile
    val dLat = radians(p2.lat) - radians(p1.lat)
    val dLon = radians(p2.lon) - radians(p1.lon)
    val a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(radians(p2.lat)) * Math.cos(radians(p1.lat)) * Math.pow(Math.sin(dLon / 2), 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    EARTH_RADIUS * c
  }

  def radians(degrees: Double): Double = degrees * Math.PI / 180

  def calculateDistance(): Double = {
    var result = 0.0;
    for (i <- 0 until points.length - 1) {
      result += distance(points(i), points(i + 1))
    }
    result
  }

  val totalTime = calculateTime()
  val totalDistance = calculateDistance()
  val pace = totalTime / 60 / totalDistance

  Record(totalTime, totalDistance, pace)
}

// main
@main def main(): Unit = {
  val points = List(
    Point(37.5139840, 126.9570770, 18.2),
    Point(37.5139730, 126.9570760, 18.1),
    Point(37.5139390, 126.9570810, 18.0),
    Point(37.5139250, 126.9570860, 17.9),
    Point(37.5139030, 126.9570920, 17.7),
    Point(37.5138600, 126.9571090, 17.3),
    Point(37.5138290, 126.9571110, 17.2),
    Point(37.5138010, 126.9571230, 17.1),
    Point(37.5137930, 126.9571270, 16.9),
    Point(37.5137620, 126.9571310, 16.8),
    Point(37.5137530, 126.9571300, 16.7),
    Point(37.5137260, 126.9571330, 16.5)
  )
  val actual = trackSummary(points)
  println(actual)
  assert(actual == Record(208.4, 0.018235238890288746, 190.47369514764472))
}
