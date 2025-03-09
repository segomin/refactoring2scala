package org.sangho.refac2scala
package ch08_4

import java.io.{BufferedWriter, ByteArrayOutputStream, OutputStream}
import java.time.OffsetDateTime
import scala.util.Using

extension (date: OffsetDateTime) {
  def toDateString: String = date.toLocalDate.toString
}

case class Photo(title: String, location: String, date: OffsetDateTime, url: String)

case class Person(name: String, photo: Photo)

def renderPerson(outStream: OutputStream, person: Person): Unit = {
  outStream.write(s"<p>${person.name}</p>\n".getBytes)
  renderPhoto(outStream, person.photo)
  emitPhotoData(outStream, person.photo)
}

def listRecentPhotos(outStream: OutputStream, photos: List[Photo]): Unit = {
  photos
    .filter(photo => photo.date.isAfter(recentDateCutoff()))
    .foreach { photo =>
      outStream.write(s"<div>\n".getBytes)
      emitPhotoData(outStream, photo)
      outStream.write(s"</div>\n".getBytes)
    }
}

def recentDateCutoff(): OffsetDateTime = OffsetDateTime.now().minusDays(10)

def renderPhoto(outStream: OutputStream, photo: Photo): Unit = {
  outStream.write(s"<img src='${photo.url}' alt='${photo.title}'>\n".getBytes)
}

def emitPhotoData(outStream: OutputStream, photo: Photo): Unit = {
  outStream.write(s"<p>제목: ${photo.title}</p>\n".getBytes)
  outStream.write(s"<p>날짜: ${photo.date.toDateString}</p>\n".getBytes)
  outStream.write(s"<p>위치: ${photo.location}</p>\n".getBytes)
}

@main def main(): Unit = {
  val oldPhoto = Photo("사진1", "서울", OffsetDateTime.now().minusDays(11), "http://photo.com/picture01.jpg")
  val recentPhoto = Photo("사진2", "서울", OffsetDateTime.now().minusDays(9), "http://photo.com/picture02.jpg")

  Using(new ByteArrayOutputStream()) { writer =>
    renderPerson(writer, Person("고객1", oldPhoto))
    val actual = writer.toString
    assert(actual ==
      s"""<p>고객1</p>
         |<img src='http://photo.com/picture01.jpg' alt='사진1'>
         |<p>제목: 사진1</p>
         |<p>날짜: ${oldPhoto.date.toDateString}</p>
         |<p>위치: 서울</p>
         |""".stripMargin)
  }.get

  Using(new ByteArrayOutputStream()) { writer =>
    listRecentPhotos(writer, List(oldPhoto, recentPhoto))
    val actual = writer.toString
    assert(actual ==
      s"""<div>
         |<p>제목: 사진2</p>
         |<p>날짜: ${recentPhoto.date.toDateString}</p>
         |<p>위치: 서울</p>
         |</div>
         |""".stripMargin)
  }.get
}
