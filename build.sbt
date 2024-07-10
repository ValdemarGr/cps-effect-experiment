val ver = "2.13.14"

ThisBuild / scalaVersion := ver

lazy val analysis = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      // "io.github.quafadas" %% "dedav4s" % "0.9.0",
      "org.typelevel" %% "cats-effect" % "3.5.4",
      "io.circe" %% "circe-core" % "0.14.8",
      "io.circe" %% "circe-generic" % "0.14.8",
      "io.circe" %% "circe-parser" % "0.14.8",
      "io.circe" %% "circe-literal" % "0.14.8",
      "io.circe" %% "circe-generic-extras" % "0.14.3",
      "org.typelevel" %% "log4cats-core"    % "2.7.0",
      "org.typelevel" %% "log4cats-slf4j"   % "2.7.0",
      "co.fs2" %% "fs2-core" % "3.10.2",
      "co.fs2" %% "fs2-io" % "3.10.2",
      "org.tpolecat" %% "skunk-core" % "0.6.4",
      "org.tpolecat" %% "natchez-core" % "0.3.5",
      "org.tpolecat" %% "natchez-noop" % "0.3.5",
      "org.http4s" %% "http4s-core" % "1.0.0-M41",
      "org.http4s" %% "http4s-dsl" % "1.0.0-M41",
      "org.http4s" %% "http4s-circe" % "1.0.0-M41",
      "org.http4s" %% "http4s-client" % "1.0.0-M41",
      "org.http4s" %% "http4s-ember-client" % "1.0.0-M41",
      "ch.qos.logback" % "logback-classic" % "1.2.9",
    )
  )
