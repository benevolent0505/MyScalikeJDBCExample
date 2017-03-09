package repository

import helper.SetupDB
import models.{GraduateSchool, UndergraduateSchool}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import org.scalatest.fixture
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{DB, NamedDB}

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by benevolent0505 on 17/03/09.
 */
class UECKyukoTest extends fixture.FlatSpec with SetupDB with AutoRollback {

  override def db(): DB = NamedDB('test).toDB()

  behavior of "UECKyuko"

  it should "fetch uec kyuukou page Document" in { implicit s =>
    val undergraduateFuture = UECKyuko.fetchKyukoPage(new UndergraduateSchool)
    val graduateFuture = UECKyuko.fetchKyukoPage(new GraduateSchool)

    undergraduateFuture.foreach { doc =>
      assert(doc.title === "電気通信大学（学域・学部）　休講のお知らせ")
    }

    graduateFuture.foreach { doc =>
      assert(doc.title === "電気通信大学（大学院）　休講のお知らせ")
    }
  }

  it should "convert html to Lecture list" in { implicit s =>
    val browser = JsoupBrowser()
    val onDay = getClass.getResource("/kyuko_on_day.html").getPath
    val offDay = getClass.getResource("/kyuko_off_day.html").getPath

    val onDayDoc = browser.parseFile(onDay, "Shift_JIS")
    val offDayDoc = browser.parseFile(offDay, "Shift_JIS")

    val noLecture = UECKyuko.getKyukoPage(offDayDoc)
    assert(noLecture.isEmpty)

    val lectures = UECKyuko.getKyukoPage(onDayDoc)
    assert(lectures.size === 3)
    assert(lectures.exists(_.name == "美術B"))
    assert(lectures.exists(_.name == "中国語運用演習"))
    assert(lectures.exists(_.name == "選択中国語第二"))
  }

  it should "convert table row values to a Lecture" in { implicit s =>
    val values = Seq("人文社会", "10月5日(水)", "5", "美術B", "○久々湊", " ")

    val lecture = UECKyuko.convertLecture(values)

    assert(lecture.category === "人文社会")
    assert(lecture.date.getMonthOfYear === 10)
    assert(lecture.date.getDayOfMonth === 5)
    assert(lecture.period === 5)
    assert(lecture.name === "美術B")
    assert(lecture.teacher.get.name === "○久々湊")
    assert(lecture.remark === " ")
  }
}
