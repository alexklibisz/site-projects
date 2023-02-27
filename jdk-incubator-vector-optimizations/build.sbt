ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.10"

lazy val root =
  project
    .in(file("."))
    .enablePlugins(JmhPlugin)
    .settings(
      name := "jdk-incubator-vector-optimizations",
      // Needed to compile the jdk.incubator.vector code.
      javacOptions ++= Seq(
        "--add-modules",
        "jdk.incubator.vector",
        "--add-exports",
        "java.base/jdk.internal.vm.vector=ALL-UNNAMED"
      ),
      // Needed to run the jdk.incubator.vector code.
      Jmh / javaOptions ++= Seq(
        "--add-modules",
        "jdk.incubator.vector"
      ),

      libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % Test
    )
