package services

import models.Teacher
import org.joda.time.LocalDateTime
import org.scalatest.FlatSpec
import repository.{TeacherRepository, UsesTeacherRepository}

/**
 * Created by mikio on 2017/03/17.
 */
class TeacherServiceTest extends FlatSpec {


  val teacher = Teacher(1l, name = "Alice", createdAt = LocalDateTime.now)

  val teacherService = new TeacherService with UsesTeacherRepository {
    override val teacherRepository: TeacherRepository = new TeacherRepository {
      def insert(teacher: Teacher): Teacher = teacher

      def find(id: Long): Option[Teacher] = if (id == 1l) Some(teacher) else None

      def find(name: String): Option[Teacher] = if (name == "Alice") Some(teacher) else None
    }
  }

  behavior of "TeacherService"

  it should "create by name" in {
    val now = LocalDateTime.now
    val created = teacherService.create("Bob", now)

    assert(created.name === "Bob", "got a different name")
    assert(created.createdAt === now, "got a different create_at")
  }

  it should "throw an exception if a name is empty" in {
    val exception = intercept[Exception] {
      teacherService.create("")
    }

    assert(exception.getMessage === "name is empty")
  }

  it should "throw an exception if this name teacher is already created" in {
    val exception = intercept[Exception] {
      teacherService.create("Alice")
    }

    assert(exception.getMessage === "Alice is already created")
  }

  it should "find a teacher by id" in {
    val teacher = teacherService.findById(1l)

    assert(teacher.isDefined)
    assert(teacher.get.id === 1l)
    assert(teacher.get.name === "Alice")
  }

  it should "find a teacher by name" in {
    val teacher = teacherService.findByName("Alice")

    assert(teacher.isDefined)
    assert(teacher.get.id === 1l)
    assert(teacher.get.name === "Alice")
  }

  it should "find or create a teacher by name" in {
    val aliceName = "Alice"
    val bobName = "Bob"
    assert(teacherService.findByName(aliceName).isDefined)
    assert(teacherService.findByName(bobName).isEmpty)

    val alice = teacherService.findOrCreateByName(aliceName)
    val bob = teacherService.findOrCreateByName(bobName)

    assert(alice.name === aliceName)
    assert(bob.name === bobName)
  }
}
