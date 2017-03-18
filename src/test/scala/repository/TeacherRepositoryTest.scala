package repository

import helper.SetupDB
import models.Teacher
import org.joda.time.LocalDateTime
import org.scalatest.fixture.FlatSpec
import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback

/**
 * Created by mikio on 2017/03/17.
 */
class TeacherRepositoryTest extends FlatSpec with AutoRollback with SetupDB {

  // TODO: Not passed
  override def db(): DB = NamedDB('test).toDB()

  behavior of "TeacherRepository"

  it should "insert a teacher and return a teacher" in { implicit session =>
    val alice = Teacher("Alice", LocalDateTime.now)

    val teacher = TeacherRepositoryImpl.insert(alice)
    assert(alice.name === teacher.name)
    assert(alice.createdAt === teacher.createdAt)
  }
}
