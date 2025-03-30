ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.2"
lazy val root = (project in file("."))
  .settings(
    name := "refactoring2scala",
    idePackagePrefix := Some("org.sangho.refac2scala"),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.10",
      "org.scalameta" %% "munit" % "1.0.4" % Test
    )
  )
