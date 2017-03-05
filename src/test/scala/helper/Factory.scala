package helper

import org.joda.time.LocalDateTime
import scalikejdbc._

import scala.util.Random

/**
 * Created by benevolent0505 on 17/03/05.
 */
object Factory {

  def createTeacher(id: Long = 0l, name: String = getRandomString("test_user"), createdAt: LocalDateTime = LocalDateTime.now)
    (implicit s: DBSession = AutoSession): Unit = {
    if (id == 0l) {
      sql"""insert into Teachers (id, name, created_at) values (${id}, ${name}, ${createdAt})""".execute().apply()
    } else {
      sql"""insert into Teachers (name, created_at) values (${name}, ${createdAt})""".execute().apply()
    }
  }

  def createLecture()(implicit s: DBSession = AutoSession): Unit = {

  }

  def getTeacherIdByName(name: String)(implicit s: DBSession = AutoSession): Option[Long] = {
    sql"""select id from Teachers where name = ${name}"""
      .map(_.long("id")).single().apply()
  }

  private def getRandomString(prefix: String = "", length: Int = 5): String = {
    prefix + "_" + Random.alphanumeric.take(length).mkString
  }
}
