name := "RoundRobinWebService"
 
version := "1.0" 
      
lazy val `roundrobinwebservice` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice,
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.typesafe" % "config" % "1.3.1",
  "io.circe" %% "circe-core" % "0.8.0",
  "io.circe" %% "circe-generic"% "0.8.0",
  "io.circe" %% "circe-parser" % "0.8.0",
  "net.codingwell" %% "scala-guice" % "4.1.1")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )