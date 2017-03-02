import scalikejdbc._
/**
 * Created by benevolent0505 on 17/02/27.
 */
object Main extends App {

  // initialize JDBC driver & connection pool
  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:test1", "user", "pass")

  // ad-hoc session provider on the REPL
  implicit val session = AutoSession

  // table creation, you can run DDL by using #execute as same as JDBC
  sql"""
        create table Teachers (
            id serial not null primary key,
            name varchar(64) not null UNIQUE,
            created_at timestamp not null
        )
    """.execute.apply()

  sql"""
        create table Lectures (
            id serial not null primary key,
            category varchar(64) not null unique,
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
