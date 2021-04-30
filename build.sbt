import sbt._
import sbt.Keys._
import ReleaseTransformations._

name := "kafka-test-api"
version := "1.0-SNAPSHOT"

lazy val scala212 = "2.12.13"
lazy val scala211 = "2.11.12"
lazy val supportedScalaVersions = List(scala212, scala211)

ThisBuild / scalaVersion := scala212

scalacOptions ++= Seq("-deprecation")

resolvers += Resolver.sonatypeRepo("releases")

releaseVersionBump := sbtrelease.Version.Bump.Bugfix
releaseProcess := releaseProcess.value.filter(_ != publishArtifacts)

libraryDependencies ++= Seq(
  "io.github.embeddedkafka" %% "embedded-kafka" % "2.8.0"
)

crossScalaVersions := supportedScalaVersions

enablePlugins(PackPlugin)
