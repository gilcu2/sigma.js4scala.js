lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)
  
name := "sigma.js façade in scala.js"

normalizedName := "sigmajs4scalajs"

version := "0.1.0-SNAPSHOT"

organization := "gilcu2"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.0",
  "org.webjars" % "sigma.js" % "1.0.3"
  )
    

jsDependencies +="org.webjars" % "sigma.js" % "1.0.3" / "sigma.min.js"