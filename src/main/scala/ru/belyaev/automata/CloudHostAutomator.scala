package ru.belyaev.automata

import com.typesafe.config.{Config, ConfigFactory}
import ru.belyaev.automata.application.{Filter, ResourceChecker}
import ru.belyaev.automata.domain.model.AwsApiClient
import ru.belyaev.automata.domain.model.resource.CloudResource

/**
  * @author avbelyaev
  */
object CloudHostAutomator {

  val conf: Config = ConfigFactory.load()

  def main(arg: Array[String]): Unit = {
    println("starting")

    val aws = new AwsApiClient()

    val nameRegex = conf.getString("aws.name-regex")
    val runtimeThreshold = conf.getInt("aws.runtime-threshold-h")
    val awsFilter = new Filter(nameRegex, runtimeThreshold)

    val awsResources = ResourceChecker.missingResources(aws, awsFilter)

    // TODO same 4 RAX

    notify(awsResources)
  }

  def notify(resources: List[CloudResource]): Unit = {
    println(s"notifying about: $resources")
  }
}
