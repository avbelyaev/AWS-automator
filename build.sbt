name := "scalakka"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.amazonaws" % "aws-java-sdk-ec2" % "1.11.446"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.20.0"
libraryDependencies += "com.typesafe" % "config" % "1.3.2"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
libraryDependencies += "org.apache.jclouds" % "jclouds-core" % "2.1.1"
libraryDependencies += "org.apache.jclouds.api" % "openstack-nova" % "2.1.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.1.1"



assemblyJarName in assembly := "automator.jar"
