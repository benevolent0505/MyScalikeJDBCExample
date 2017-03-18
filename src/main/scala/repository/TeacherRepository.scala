package repository

import models.Teacher
import scalikejdbc._

/**
  * Created by mikio on 2017/03/16.
  * infra layer
  */
trait UsesTeacherRepository {
  val teacherRepository: TeacherRepository
}

trait TeacherRepository {
  def insert(teacher: Teacher): Teacher

  def find(id: Long): Option[Teacher]

  def find(name: String): Option[Teacher]
}

trait MixInTeacherRepository extends UsesTeacherRepository {
  val teacherRepository = TeacherRepositoryImpl
}

object TeacherRepositoryImpl extends TeacherRepository {
  def * = (rs: WrappedResultSet) => Teacher(rs.long("id"), rs.string("name"), rs.jodaLocalDateTime("created_at"))

  def insert(teacher: Teacher): Teacher = DB localTx { implicit s =>
    val id =
      sql"""
           insert into Teachers
             (name, created_at)
           values (${teacher.name}, ${teacher.createdAt})
        """.updateAndReturnGeneratedKey.apply()

    Teacher(id = id, teacher.name, teacher.createdAt)
  }

  def find(id: Long): Option[Teacher] = DB localTx { implicit s =>
    sql"""select * from Teachers where id = ${id}""".map(*).single().apply()
  }

  def find(name: String): Option[Teacher] = DB localTx { implicit s =>
    sql"""select * from Teachers where name = ${name}""".map(*).single().apply()
  }
}
