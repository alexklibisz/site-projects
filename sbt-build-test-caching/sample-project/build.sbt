ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.8"

lazy val root = project
  .in(file("."))
  .settings(
    name := "sample-project"
  )
  .aggregate(
    `math-add`,
    `math-exp`,
    `math-pythagorean`
  )

lazy val `math-add` = project
  .in(file("math-add"))
  .settings(subprojectSettings)

lazy val `math-exp` = project
  .in(file("math-exp"))
  .settings(subprojectSettings)

lazy val `math-pythagorean` = project
  .in(file("math-pythagorean"))
  .dependsOn(`math-add`, `math-exp`)
  .settings(subprojectSettings)


lazy val subprojectSettings = List(
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.13" % Test
)
