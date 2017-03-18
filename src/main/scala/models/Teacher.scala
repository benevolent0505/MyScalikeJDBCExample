package models

import org.joda.time.LocalDateTime

/**
 * Created by benevolent0505 on 17/02/27.
 */
case class Teacher (
  id: Long,
  name: String,
  createdAt: LocalDateTime
)

object Teacher {
  def apply(
    name: String,
    createdAt: LocalDateTime
  ): Teacher = new Teacher(0l, name, createdAt)
}
