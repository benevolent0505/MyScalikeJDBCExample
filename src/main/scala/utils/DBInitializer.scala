package utils

import scalikejdbc._

/**
 * Created by benevolent0505 on 17/03/04.
 */
object DBInitializer {

  def run(name: Symbol): Unit = {
    NamedDB(name) localTx { implicit session =>
      try {
        sql"""select 1 from Teachers limit 1""".map(_.long(1)).single().apply()
      } catch {
        case e: java.sql.SQLException =>
          sql"""
                create table Teachers (
                    id serial not null primary key,
                    name varchar(64) not null UNIQUE,
                    created_at timestamp not null
                )
              """.execute.apply()
      }

      try {
        sql"""select 1 from Lectures limit 1""".map(_.long(1)).single().apply()
      } catch {
        case e: java.sql.SQLException =>
          sql"""
                create table Lectures (
                    id serial not null primary key,
                    category varchar(64) not null,
                    date timestamp not null,
                    period int not null,
                    name varchar(64) not null,
                    teacher_id int not null,
                    remark varchar(64) not null,
                    created_at timestamp not null,
                    updated_at timestamp not null
                )
              """.execute().apply()
      }
    }
  }
}
