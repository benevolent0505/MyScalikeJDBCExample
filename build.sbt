name := "my-scalikejdbc-example"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"        % "2.5.0",
  "org.scalikejdbc" %% "scalikejdbc-config" % "2.5.0",
  "com.h2database"  %  "h2"                 % "1.4.193",
  "ch.qos.logback"  %  "logback-classic"    % "1.1.7",

  "net.ruippeixotog" %% "scala-scraper" % "1.2.0",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",

  "org.scalactic"   %% "scalactic" % "3.0.1",
  "org.scalatest"   %% "scalatest" % "3.0.1" % "test",
  "org.scalikejdbc" %% "scalikejdbc-test" % "2.5.0" % "test"
)

initialCommands := """
    import scalikejdbc._, config._
    import utils._
    DBs.setupAll()
    DBInitializer.run()
  """