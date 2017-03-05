package repository

import helper.{Factory, SetupDB}
import org.joda.time.LocalDateTime
import org.scalatest.fixture
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{DB, DBSession, NamedDB}

/**
 * Created by benevolent0505 on 17/02/28.
 */
class TeachersTest extends fixture.FlatSpec with AutoRollback with SetupDB {

  behavior of "Teachers"

  override def db(): DB = NamedDB('test).toDB()

  val aliceName = "Alice"
  val aliceCreated = new LocalDateTime(2017, 2, 1, 0, 0, 0)

  override def fixture(implicit session: DBSession): Unit = {
    Factory.createTeacher(name = aliceName, createdAt = aliceCreated)
  }

  it should "create a Teacher by name" in { implicit session =>
    val name = "Bob"
    val teacher = Teachers.createByName(name = name)

    assert(teacher.name == name)
  }

  it should "find a teacher by id" in { implicit session =>
    val aliceId = Factory.getTeacherIdByName(aliceName).get
    val found = Teachers.findById(aliceId).get

    assert(found.name == aliceName)
    assert(found.createdAt == aliceCreated)
  }

  it should "find a teacher by name" in { implicit session =>
    val found = Teachers.findByName(aliceName).get

    assert(found.name == aliceName)
    assert(found.createdAt == aliceCreated)
  }

  it should "find or create teacher by name" in { implicit session =>
    val name = "charlie"

    val found = Teachers.findOrCreateByName(aliceName)
    val created = Teachers.findOrCreateByName(name)

    assert(found.name == aliceName)
    assert(found.createdAt == aliceCreated)

    assert(created.name == name)
  }

  it should "find all teacher" in { implicit session =>

  }
}