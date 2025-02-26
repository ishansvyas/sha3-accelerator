ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "1.3.0"
ThisBuild / organization := "edu.duke"

val chiselVersion = "3.5.5"

lazy val root = (project in file("."))
  .settings(
    name := "cs590-lab1",
    libraryDependencies ++= Seq(
      "edu.berkeley.cs" %% "chisel3" % chiselVersion,
      "edu.berkeley.cs" %% "chiseltest" % "0.5.4" % "test",
      "edu.berkeley.cs" %% "chiseltest" % "0.5.4"
    ),
    scalacOptions ++= Seq(
      "-language:reflectiveCalls",
      "-deprecation",
      "-feature",
      "-Xcheckinit",
      "-P:chiselplugin:genBundleElements"
    ),
    addCompilerPlugin(("edu.berkeley.cs" % "chisel3-plugin" % chiselVersion).cross(CrossVersion.full))
  )

logBuffered in Test := false

