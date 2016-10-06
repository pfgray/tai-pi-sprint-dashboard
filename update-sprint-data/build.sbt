name := "update-sprint-data"
version := "1.0"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "2.3.0"
)

resolvers += Resolver.sonatypeRepo("public")

mainClass in Compile := Some("com.learningobjects.tai.Main")