val Scala2_11  = "2.11.12"
val Scala2_12  = "2.12.8"
val ScalaTest  = "3.0.8"

val SharedSettings = Seq(
  name := "translit-scala",
  organization := "tech.sparse",
  scalaVersion := Scala2_12,
  crossScalaVersions := Seq(Scala2_12, Scala2_11),
  scalacOptions := Seq(
    "-unchecked",
    "-deprecation",
    "-encoding", "utf8"
  ),
  pomExtra :=
    <url>https://github.com/sparsetech/translit-scala</url>
    <licenses>
      <license>
        <name>Apache-2.0</name>
        <url>https://www.apache.org/licenses/LICENSE-2.0.html</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:sparsetech/translit-scala.git</url>
    </scm>
    <developers>
      <developer>
        <id>tindzk</id>
        <name>Tim Nieradzik</name>
        <url>http://github.com/tindzk/</url>
      </developer>
    </developers>
)

lazy val root = project.in(file("."))
  .aggregate(js, jvm)
  .settings(SharedSettings: _*)
  .settings(publishArtifact := false)

lazy val translit = crossProject.in(file("."))
  .settings(SharedSettings: _*)
  .settings(
    autoAPIMappings := true,
    apiMappings += (scalaInstance.value.libraryJar -> url(s"http://www.scala-lang.org/api/${scalaVersion.value}/")),
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % ScalaTest % "test"
    )
  )
  .jsSettings(
    /* Use io.js for faster compilation of test cases */
    scalaJSStage in Global := FastOptStage
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "com.github.pathikrit" %%% "better-files" % "3.8.0" % "test",
      "org.scalaj" %%% "scalaj-http" % "2.4.2" % "test"
    )
  )

lazy val js  = translit.js
lazy val jvm = translit.jvm
