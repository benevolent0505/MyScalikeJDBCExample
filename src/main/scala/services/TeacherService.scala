package services

import models.Teacher
import org.joda.time.LocalDateTime
import repository.{MixInTeacherRepository, UsesTeacherRepository}

/**
  * Created by mikio on 2017/03/16.
  * domain layer
  */
trait TeacherService extends UsesTeacherRepository {

  def create(name: String, createdAt: LocalDateTime = LocalDateTime.now): Teacher = {
    if (name.isEmpty) throw new Exception("name is empty")
    if (findByName(name).isDefined) throw new Exception(s"$name is already created")

    teacherRepository.insert(Teacher(name, createdAt))
  }

  def findById(id: Long): Option[Teacher] =
    teacherRepository.find(id)

  def findByName(name: String): Option[Teacher] =
    teacherRepository.find(name)

  def findOrCreateByName(name: String): Teacher =
    findByName(name)
      .getOrElse(create(name))
}

object TeacherService extends TeacherService with MixInTeacherRepository
