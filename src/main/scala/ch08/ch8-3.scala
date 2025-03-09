package org.sangho.refac2scala
package ch08_3

import java.time.OffsetDateTime

extension (date: OffsetDateTime) {
  def toDateString: String = date.toLocalDate.toString
}

case class Photo(title: String, location: String, date: OffsetDateTime, url: String)

case class Person(name: String, photo: Photo)

def renderPerson(person: Person): String = List(
  s"<p>${person.name}</p>",
  renderPhoto(person.photo),
  zznew(person.photo),
).mkString("\n")

def photoDiv(photo: Photo) = List(
  "<div>",
  zznew(photo),
  "</div>"
).mkString("\n")

def zznew(photo: Photo) = List(
  s"<p>제목: ${photo.title}</p>",
  emitPhotoData(photo)
).mkString("\n")

def renderPhoto(photo: Photo) = s"<img src='${photo.url}' alt='${photo.title}'>"

def emitPhotoData(photo: Photo): String = List(
  s"<p>위치: ${photo.location}</p>",
  s"<p>날짜: ${photo.date.toDateString}</p>"
).mkString("\n")

@main def main(): Unit = {
  val time = OffsetDateTime.of(2025, 3, 9, 0, 0, 0, 0, OffsetDateTime.now().getOffset)
  val photo = Photo("사진1", "서울", time, "http://photo.com/picture01.jpg")
  val person = Person("고객1", photo)
  val actual = renderPerson(person)
  assert(actual ==
    """<p>고객1</p>
      |<img src='http://photo.com/picture01.jpg' alt='사진1'>
      |<p>제목: 사진1</p>
      |<p>위치: 서울</p>
      |<p>날짜: 2025-03-09</p>""".stripMargin)

  val actualDiv = photoDiv(photo)
  assert(actualDiv ==
    """<div>
      |<p>제목: 사진1</p>
      |<p>위치: 서울</p>
      |<p>날짜: 2025-03-09</p>
      |</div>""".stripMargin)
}
