package ru.belyaev.automata

import com.typesafe.config.{Config, ConfigFactory}
import ru.belyaev.automata.application.{Filter, PrettyPrinter, ResourceChecker}
import ru.belyaev.automata.domain.model.resource.CloudResource
import ru.belyaev.automata.domain.model.{AwsApiClient, RaxApiClient}

/**
  * @author avbelyaev
  */
object CloudHostAutomator {

  val conf: Config = ConfigFactory.load()

  def main(arg: Array[String]): Unit = {
    println("starting")

    val missingResources = awsResources() //.union(raxResources())

    val table = PrettyPrinter.generateTable(
      missingResources.map(resource => resource.toTableRow),
      CloudResource.tableHeader
    )

    notify(table)
  }

  def awsResources(): List[CloudResource] = {
    val aws = new AwsApiClient()

    val nameRegex = conf.getString("aws.name-regex")
    val runtimeThreshold = conf.getInt("aws.runtime-threshold-h")
    val filter = new Filter(nameRegex, runtimeThreshold)

    ResourceChecker.missingResources(aws, filter)
  }

  def raxResources(): List[CloudResource] = {
    val rax = new RaxApiClient()

    val nameRegex = conf.getString("rax.name-regex")
    val runtimeThreshold = conf.getInt("rax.runtime-threshold-h")
    val filter = new Filter(nameRegex, runtimeThreshold)

    ResourceChecker.missingInstances(rax, filter)
  }

  def notify(data: String): Unit = {
    println(s"notifying about: $data")
  }
}
