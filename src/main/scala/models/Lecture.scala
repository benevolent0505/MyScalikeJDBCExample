package models

import org.joda.time.LocalDateTime

/**
 * Created by benevolent0505 on 17/02/27.
 */
case class Lecture (
  id: Long,
  category: String,
  date: LocalDateTime,
  period: Int,
  name: String,
  teacher: Option[Teacher],
  remark: String,
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime
)
