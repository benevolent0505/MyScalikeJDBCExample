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
}
