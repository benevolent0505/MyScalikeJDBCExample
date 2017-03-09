package models

/**
 * Created by benevolent0505 on 17/03/04.
 */
sealed trait School {
  val url: String
}

case class UndergraduateSchool() extends School {
  val url = "http://kyoumu.office.uec.ac.jp/kyuukou/kyuukou.html"
}

case class GraduateSchool() extends School {
  val url = "http://kyoumu.office.uec.ac.jp/kyuukou/kyuukou2.html"
}