package repository

import java.nio.charset.Charset

import dispatch.Http
import models.{Lecture, School}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.{Document, Element}
import org.joda.time.LocalDateTime
import scalikejdbc.{AutoSession, DBSession}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by benevolent0505 on 17/03/04.
 */
object UECKyuko {

  def fetchKyukoPage(school: School): Future[Document] = {
    val browser = JsoupBrowser()

    Http(dispatch.url(school.url) OK dispatch.as.String.charset(Charset.forName("Shift_JIS"))).map {
      browser.parseString
    }
  }

  def getKyukoPage(doc: Document)(implicit s: DBSession = AutoSession): Seq[Lecture] = {
    val isNoSchedule = doc.body.text.contains("現在、休講の予定はありません。")
    val items: Option[List[Element]] = doc >?> elementList("tr")

    if (!isNoSchedule && items.isDefined) {
      items.get.tail
        .map(_ >> elementList("td").map(_ >> text("td")))
        .map(convertLecture)
    } else List.empty[Lecture]
  }

  def convertLecture(values: Seq[String])(implicit s: DBSession = AutoSession): Lecture = {
    val regexp = """(\d+)月(\d+)日.+""".r
    val now = LocalDateTime.now
    val regexp(month, day) = values(1).trim
    val year = if (month.toInt > now.getMonthOfYear) {
      now.getYear
    } else {
      now.getYear + 1
    }
    val date = new LocalDateTime(year, month.toInt, day.toInt, 0, 0)

    Lectures.findOrCreate(
      category = values(0),
      date = date,
      period = values(2).toInt,
      name = values(3),
      teacherName = values(4),
      remark = values(5)
    )
  }
}
