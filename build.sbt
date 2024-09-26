name := """Api_Alumnos"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.15"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
libraryDependencies += "org.webjars" % "bootstrap" % "5.3.3"
libraryDependencies += "org.webjars" % "jquery" % "3.7.1"
libraryDependencies ++= Seq(
  guice,
  "com.typesafe.play" %% "play-json" % "2.10.5",
)
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.3.0"
libraryDependencies += "org.postgresql" % "postgresql" % "42.7.3"
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
