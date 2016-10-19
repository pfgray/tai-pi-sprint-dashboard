name := "update-sprint-data"
version := "1.0"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "com.typesafe.play" %% "play-json" % "2.5.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "joda-time" %% "joda-time" %% "2.9.4"
)

resolvers += Resolver.sonatypeRepo("public")
//resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

mainClass in Compile := Some("com.learningobjects.tai.Main")