package ru.belyaev.automata

import ru.belyaev.automata.application.{Filter, ResourceChecker}
import ru.belyaev.automata.domain.model.AwsApiClient
import ru.belyaev.automata.domain.model.resource.CloudResource

/**
  * @author avbelyaev
  */
object CloudHostAutomator {

  def main(arg: Array[String]): Unit = {

    println("starting")

    val region = "eu-west-3"
    val aws = new AwsApiClient(accessKey, secret, region)

    val awsFilter = new Filter(AWS_SERVER_NAME_REGEX, AWS_RUNTIME_THRESHOLD_HOURS)
    val awsResources = ResourceChecker.missingResources(aws, awsFilter)

    // TODO same 4 RAX

    notify(awsResources)
  }

  def notify(resources: List[CloudResource]): Unit = {
    println(s"notifying about: $resources")
  }
}
