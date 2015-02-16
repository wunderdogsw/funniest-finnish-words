name := "funny-words--one-result-scala"

organization := "wunderdog"

version := "0.0.1"

scalaVersion := "2.11.5"

initialCommands := "import wd._"


// Compiler settings. Use scalac -X for other options and their description.
// See Here for more info http://www.scala-lang.org/files/archive/nightly/docs/manual/html/scalac.html
scalacOptions ++= List("-feature","-deprecation", "-unchecked", "-Xlint")

scalaSource in Compile := baseDirectory.value / "src"

