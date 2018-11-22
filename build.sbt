name := "scalakka"

version := "0.1"

scalaVersion := "2.12.7"


// region compile
// basic
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.20.0"
libraryDependencies += "com.typesafe" % "config" % "1.3.2"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.1.1"
libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.3.0"

// logging
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"

// aws
libraryDependencies += "com.amazonaws" % "aws-java-sdk-ec2" % "1.11.446"

// mail
libraryDependencies += "com.sun.mail" % "javax.mail" % "1.6.2"
libraryDependencies += "javax.mail" % "javax.mail-api" % "1.6.2"

// jclouds
libraryDependencies += "org.apache.jclouds" % "jclouds-core" % "2.1.1"
libraryDependencies += "org.apache.jclouds.api" % "openstack-nova" % "2.1.1" //+
libraryDependencies += "org.apache.jclouds.api" % "rackspace-cloudidentity" % "2.1.1" //+
libraryDependencies += "org.apache.jclouds.provider" % "rackspace-cloudservers-us" % "2.1.1"
// endregion

// tests
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"


assemblyJarName in assembly := "automator.jar"
