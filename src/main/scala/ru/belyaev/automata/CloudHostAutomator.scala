package ru.belyaev.automata

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.{LazyLogging, Logger}
import org.slf4j.LoggerFactory
import ru.belyaev.automata.application.{Filter, PrettyPrinter, ResourceChecker}
import ru.belyaev.automata.domain.model.{AwsApiClient, CloudResource, RaxApiClient}

/**
  * @author avbelyaev
  */
object CloudHostAutomator {

  val conf: Config = ConfigFactory.load()

  val logger = Logger(LoggerFactory.getLogger(this.getClass))


  def main(arg: Array[String]): Unit = {
    logger.info("Performing runtime threshold check")

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
    logger.info(s"Aws config: $filter")

    ResourceChecker.missingResources(aws, filter)
  }

  def raxResources(): List[CloudResource] = {
    val rax = new RaxApiClient()

    val nameRegex = conf.getString("rax.name-regex")
    val runtimeThreshold = conf.getInt("rax.runtime-threshold-h")
    val filter = new Filter(nameRegex, runtimeThreshold)
    logger.info(s"Rackspace config: $filter")

    ResourceChecker.missingInstances(rax, filter)
  }

  def notify(data: String): Unit = {
    println(s"notifying: $data")
  }
}
