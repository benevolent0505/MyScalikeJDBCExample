package repository

import helper.SetupDB
import org.joda.time.LocalDateTime
import org.scalatest.fixture
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{DB, DBSession, NamedDB, SQL}

/**
 * Created by benevolent0505 on 17/03/05.
 */
class LecturesTest extends fixture.FlatSpec with SetupDB with AutoRollback {

  override def db(): DB = NamedDB('test).toDB()

  override def fixture(implicit session: DBSession): Unit = {
    SQL("insert into Teachers values (?, ?, ?)").bind(1, "久々湊", LocalDateTime.now).update().apply()
    SQL("insert into Lectures values (?, ?, ?, ?, ?, ?, ?, ?, ?)")
      .bind(1, "人文社会", new LocalDateTime(2016, 8, 3, 0, 0, 0), 5, "美術A", 1, "",
        LocalDateTime.now, LocalDateTime.now).update().apply()

    SQL("insert into Teachers values (?, ?, ?)").bind(2, "浅井", LocalDateTime.now).update().apply()
    SQL("insert into Lectures values (?, ?, ?, ?, ?, ?, ?, ?, ?)")
      .bind(2, "2昼", new LocalDateTime(2016, 4, 13, 0, 0, 0), 5, "物理学概論第一(再履修)", 2, "初回授業は4月20日(水)になります",
        LocalDateTime.now, LocalDateTime.now).update().apply()
  }

  behavior of "Lectures"

  it should "create a Lecture by some params" in { implicit s =>
    val (category, date, period, name, teacherName, remark) =
      ("人文社会", new LocalDateTime(2016, 10, 5, 0, 0, 0), 5, "美術B", "久々湊", "")

    val created = Lectures.create(
      category = category,
      date = date,
      period = period,
      name = name,
      teacherName = teacherName,
      remark = remark
    )
    val teacher = created.teacher.get

    assert(created.category === category)
    assert(created.date === date)
    assert(created.period === period)
    assert(created.name === name)
    assert(teacher.name === teacherName)
    assert(created.remark === remark)
  }

  it should "find or create lecture by some params" in { implicit s =>
    val (category1, date1, period1, name1, teacherName1, remark1) =
      ("2昼", new LocalDateTime(2016, 4, 13, 0, 0, 0), 5, "物理学概論第一(再履修)", "浅井", "初回授業は4月20日(水)になります")
    val (category2, date2, period2, name2, teacherName2, remark2) =
      ("人文社会", new LocalDateTime(2016, 10, 5, 0, 0, 0), 5, "美術B", "久々湊", "")

    val found = Lectures.findOrCreate(category1, date1, period1, name1, teacherName1, remark1)
    val created = Lectures.findOrCreate(category2, date2, period2, name2, teacherName2, remark2)

    assert(found.name === name1)
    assert(found.date === date1)

    assert(created.name === name2)
    assert(created.date === date2)
  }

  it should "find a lecture by lecture name" in { implicit s =>
    val found = Lectures.findByName("美術A").get

    assert(found.name === "美術A")
  }

  it should "find lectures by Teacher" in { implicit s =>
    val teacher = Teachers.findOrCreateByName("久々湊")
    val lectures = Lectures.findByTeacher(teacher)

    assert(lectures.size === 1)
  }

  it should "find all lectures" in { implicit s =>
    val lectures = Lectures.findAll()

    assert(lectures.size === 2)
  }
}
