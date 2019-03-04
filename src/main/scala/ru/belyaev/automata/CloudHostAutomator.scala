package ru.belyaev.automata

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import ru.belyaev.automata.application.{Filter, ResourceChecker}
import ru.belyaev.automata.domain.model.cloud.CloudResource
import ru.belyaev.automata.port.adapter.cloud.aws.AwsProvider
import ru.belyaev.automata.port.adapter.cloud.rax.RaxProvider
import ru.belyaev.automata.port.adapter.{EmailService, PrettyPrinter}

/**
  * @author avbelyaev
  */
object CloudHostAutomator {

  val conf: Config = ConfigFactory.load()

  val logger = Logger(LoggerFactory.getLogger(this.getClass))


  def main(arg: Array[String]): Unit = {
    logger.info("Performing runtime threshold check")

    val missingResources = raxResources()
    logger.info(s"Missing resources:\n$missingResources")

    val table = PrettyPrinter.generateTable(missingResources)

    notify(table)
  }

  def awsResources(): List[CloudResource] = {
    val nameRegex = conf.getString("aws.name-regex")
    val runtimeThreshold = conf.getInt("aws.runtime-threshold-h")
    val filter = new Filter(nameRegex, runtimeThreshold)
    logger.info(s"Aws config: $filter")

    ResourceChecker.missingResources(new AwsProvider(), filter)
  }

  def raxResources(): List[CloudResource] = {
    val nameRegex = conf.getString("rax.name-regex")
    val runtimeThreshold = conf.getInt("rax.runtime-threshold-h")
    val filter = new Filter(nameRegex, runtimeThreshold)
    logger.info(s"Rackspace config: $filter")

    ResourceChecker.missingResources(new RaxProvider(), filter)
  }

  def notify(message: String): Unit = new EmailService().sendMsg(message)
}
