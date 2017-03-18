import scalikejdbc._
import scalikejdbc.config.DBs
import services.TeacherService
import utils.DBInitializer
/**
 * Created by benevolent0505 on 17/02/27.
 */
object Main extends App {

  // initialize JDBC driver & connection pool
  Class.forName("org.postgresql.Driver")
  ConnectionPool.singleton("jdbc:postgresql://localhost:5432/developmentdb", "nobody", "nobody")

  DBs.setup('development)
  DBInitializer.run('development)

  val alice = TeacherService.create("Alice")
  TeacherService.findById(alice.id)
  TeacherService.findByName(alice.name)

  TeacherService.findOrCreateByName("Bob")
}
