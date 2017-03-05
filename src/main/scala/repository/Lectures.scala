package repository

import models.{Lecture, Teacher}
import org.joda.time.LocalDateTime
import scalikejdbc.{AutoSession, DBSession, WrappedResultSet, _}

/**
 * Created by benevolent0505 on 17/02/27.
 */
object Lectures {

  def *(implicit s: DBSession = AutoSession) = (rs: WrappedResultSet) => Lecture(
    rs.long("id"),
    rs.string("category"),
    rs.jodaLocalDateTime("date"),
    rs.int("period"),
    rs.string("name"),
    Teachers.findById(rs.long("teacher_id")),
    rs.string("remark"),
    rs.jodaLocalDateTime("created_at"),
    rs.jodaLocalDateTime("updated_at")
  )

  def create(category: String, date: LocalDateTime, period: Int, name: String, teacherName: String, remark: String,
    createdAt: LocalDateTime = LocalDateTime.now, updatedAt: LocalDateTime = LocalDateTime.now)(implicit s: DBSession = AutoSession): Lecture = {
    val teacher = Teachers.findOrCreateByName(teacherName)
    val id: Long =
      sql"""
           insert into Lectures (category, date, period, name, teacher_id, remark, created_at, updated_at)
           values (${category}, ${date}, ${period}, ${name}, ${teacher.id}, ${remark}, ${createdAt}, ${updatedAt})"""
      .updateAndReturnGeneratedKey.apply()

    Lecture(id, category, date, period, name, Some(teacher), remark, createdAt, updatedAt)
  }

  def findById(id: Long)(implicit s: DBSession = AutoSession): Option[Lecture] = {
    sql"""select * from Lectures Where id = ${id}"""
      .map(*).single().apply()
  }

  def findByName(name: String)(implicit s: DBSession = AutoSession): Option[Lecture] = {
    sql"""select * from Lectures where name = ${name}"""
      .map(*).single().apply()
  }

  // TODO: Paging
  def findByTeacher(teacher: Teacher)(implicit s: DBSession = AutoSession): Seq[Lecture] = {
    sql"""select * from Lectures where teacher_id = ${teacher.id}"""
      .map(*).list().apply()
  }

  def findAll()(implicit s: DBSession = AutoSession): Seq[Lecture] = {
    sql"""select * from Lectures"""
      .map(*).list().apply()
  }
}
