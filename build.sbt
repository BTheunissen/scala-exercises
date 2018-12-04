name := "scala-exercises"

version := "1.0"

scalaVersion := "2.12.6"

// We force the timezone to UTC to be consistent between deving on host and within docker
// To set this parameter we need to enable forking to a separate JVM process
fork := true

javaOptions += "-Duser.timezone=UTC"

resolvers += "mvnrepository" at "http://mvnrepository.com/artifact/"

val catsVersion = "1.4.0"
val fastParseVersion = "2.0.4"
val specs2Version = "4.3.4"

libraryDependencies ++= Seq(
  "org.typelevel"               %% "cats-core"                        % catsVersion,
  "com.lihaoyi"                 %% "fastparse"                        % fastParseVersion,
  "org.specs2"                  %% "specs2-core"                      % specs2Version             % Test,
  "org.specs2"                  %% "specs2-matcher-extra"             % specs2Version             % Test,
  "org.specs2"                  %% "specs2-scalacheck"                % specs2Version             % Test,
  "org.typelevel"               %% "cats-laws"                        % catsVersion               % Test
)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-unused-import",
  "-language:higherKinds",
  "-Ypartial-unification",
  "-Xfatal-warnings"
)

scalacOptions in Test ++= Seq("-Yrangepos")

scalacOptions in Test --= Seq(
  "-Ywarn-value-discard",
  "-Ywarn-numeric-widen"
)

//Allows doing: > testOnly *ExampleSpec -- -ex "this is example two"
testFrameworks := Seq(TestFrameworks.Specs2)
