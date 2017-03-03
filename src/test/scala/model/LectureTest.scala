package model

import models.{Lecture, Teacher}
import org.joda.time.LocalDateTime
import org.scalatest.FlatSpec

/**
 * Created by benevolent0505 on 17/03/03.
 */
class LectureTest extends FlatSpec {

  "A Lecture" should "have id, category, date, period, name, teacher, remark, createdAt, updatedAt" in {
    val id: Long = 1l
    val category = "クラス1"
    val date = LocalDateTime.now
    val period = 1
    val name = "解析学"
    val teacher = Some(Teacher(1l, "工藤", LocalDateTime.now))
    val remark = ""
    val createdAt = LocalDateTime.now
    val updatedAt = LocalDateTime.now

    val lecture = Lecture(
      id = id,
      category = category,
      date = date,
      period = period,
      name = name,
      teacher = teacher,
      remark = remark,
      createdAt = createdAt,
      updatedAt = updatedAt
    )

    assert(lecture.id == id)
    assert(lecture.category == category)
    assert(lecture.date == date)
    assert(lecture.period == period)
    assert(lecture.name == name)
    assert(lecture.teacher == teacher)
    assert(lecture.remark == remark)
    assert(lecture.createdAt == createdAt)
    assert(lecture.updatedAt == updatedAt)
  }
}
