import sbt._
import sbt.Keys._
import com.typesafe.sbt.SbtScalariform._
import com.typesafe.sbt.SbtScalariform.ScalariformKeys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import sbtassembly._
import sbtassembly.AssemblyKeys._
import com.earldouglas.xwp.JettyPlugin

object TodoApiBuild extends Build {
  import scalariform.formatter.preferences._
  import Dependencies._

  val baseSettings = Defaults.coreDefaultSettings ++ scalariformSettings ++ Seq(
    organization := "org.usagram",
    version := "0.0.1",
    scalaVersion := "2.12.2",
    scalacOptions ++= Seq(
      "-target:jvm-1.8",
      "-unchecked",
      "-deprecation",
      "-Xcheckinit",
      "-encoding",
      "utf8",
      "-feature",
      "-language:higherKinds",
      "-language:postfixOps",
      "-language:implicitConversions"
    ),
    preferences := preferences.value
      .setPreference(AlignParameters, true)
      .setPreference(AlignArguments, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(CompactControlReadability, true),
    test in assembly := {}
  )

  lazy val todoApi = Project(
    id       = "todoApi",
    base     = file("."),
    settings = baseSettings
  ).aggregate(domain, jdbcImpl, app)

  lazy val app = Project(
    id       = "app",
    base     = file("app"),
    settings = baseSettings ++ ScalatraPlugin.scalatraSettings ++ Seq(
      parallelExecution in Test := false,
      libraryDependencies ++= Seq(
        scalatra,
        scalatraJson,
        servletApi % "provided",
        json4sCore,
        json4sJackson,
        scalikejdbcConfig,
        scalatraScalatest % "test"
      )
    )
  ).dependsOn(domain, jdbcImpl).enablePlugins(JettyPlugin)

  lazy val jdbcImpl = Project(
    id       = "jdbcImpl",
    base     = file("jdbc_impl"),
    settings = baseSettings ++ Seq(
      libraryDependencies ++= Seq(
        scalikejdbc,
        postgresql,
        logbackClassic % "runtime"
      )
    )
  ).dependsOn(domain)

  lazy val domain = Project(
    id       = "domain",
    base     = file("domain"),
    settings = baseSettings ++ Seq(
      libraryDependencies ++= Seq(
        jodaTime,
        jodaConvert,
        scalatest % "test"
      )
    )
  )
}
