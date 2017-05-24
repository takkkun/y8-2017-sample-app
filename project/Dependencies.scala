import sbt._

object Dependencies {
  val jodaTime          = "joda-time"       %  "joda-time"          % "2.9.9"
  val jodaConvert       = "org.joda"        %  "joda-convert"       % "1.8"
  val scalikejdbc       = "org.scalikejdbc" %% "scalikejdbc"        % "3.0.0"
  val scalikejdbcConfig = "org.scalikejdbc" %% "scalikejdbc-config" % "3.0.0"
  val postgresql        = "org.postgresql"  %  "postgresql"         % "42.1.1"
  val logbackClassic    = "ch.qos.logback"  %  "logback-classic"    % "1.2.3"
  val scalatra          = "org.scalatra"    %% "scalatra"           % "2.5.0"
  val scalatraJson      = "org.scalatra"    %% "scalatra-json"      % "2.5.0"
  val servletApi        = "javax.servlet"   %  "javax.servlet-api"  % "3.1.0"
  val json4sCore        = "org.json4s"      %% "json4s-core"        % "3.5.2"
  val json4sJackson     = "org.json4s"      %% "json4s-jackson"     % "3.5.2"
  val scalatest         = "org.scalatest"   %% "scalatest"          % "3.0.1"
  val scalatraScalatest = "org.scalatra"    %% "scalatra-scalatest" % "2.5.0"
}
