package repository

import models.Teacher
import org.joda.time.LocalDateTime
import scalikejdbc.{AutoSession, DBSession, _}

/**
 * Created by benevolent0505 on 17/02/28.
 */
object Teachers {

  def * = (rs: WrappedResultSet) => Teacher(rs.long("id"), rs.string("name"), rs.jodaLocalDateTime("created_at"))

  def createByName(name: String, createdAt: LocalDateTime = LocalDateTime.now)(implicit s: DBSession = AutoSession): Teacher = {
    val id = sql"""insert into Teachers (name, created_at) values (${name}, ${createdAt})"""
      .updateAndReturnGeneratedKey.apply()

    Teacher(id, name, createdAt)
  }

  def findById(id: Long)(implicit s: DBSession = AutoSession): Option[Teacher] = {
    sql"""select * from Teachers WHERE id = ${id}"""
      .map(*).single().apply()
  }

  def findByName(name: String)(implicit s: DBSession = AutoSession): Option[Teacher] = {
    sql"SELECT * FROM Teachers WHERE name = ${name}"
      .map(*).single().apply()
  }

  def findOrCreateByName(name: String)(implicit s: DBSession = AutoSession): Teacher = {
    findByName(name) match {
      case Some(teacher) => teacher
      case None => createByName(name)
    }
  }

  def findAll()(implicit s: DBSession = AutoSession): Seq[Teacher] = {
    sql"""select * from Teachers"""
      .map(*).list().apply()
  }
}
