package helper

import org.scalatest.{BeforeAndAfterAll, fixture}
import scalikejdbc.config.DBs
import utils.DBInitializer

/**
 * Created by benevolent0505 on 17/03/03.
 */
trait SetupDB extends BeforeAndAfterAll { self: fixture.FlatSpec =>

  override def beforeAll(): Unit = {
    DBs.setup('test)
    DBInitializer.run('test)

    super.beforeAll()
  }

  override def afterAll(): Unit = {
    DBs.closeAll()
    super.afterAll()
  }
}
