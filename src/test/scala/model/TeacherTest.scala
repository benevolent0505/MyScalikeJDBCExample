package model

import models.Teacher
import org.joda.time.LocalDateTime
import org.scalatest.FlatSpec

/**
 * Created by benevolent0505 on 17/03/03.
 */
class TeacherTest extends FlatSpec {

  "A Teacher" should "have id, name, createdAt" in {
    val id: Long = 1l
    val name = "竹内"
    val createdAt = LocalDateTime.now

    val teacher = Teacher(id = id, name = name, createdAt = createdAt)
    assert(teacher.id == id)
    assert(teacher.name == name)
    assert(teacher.createdAt == createdAt)
  }
}
