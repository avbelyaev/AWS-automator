package ru.belyaev.automata.domain.model

import java.util.concurrent.TimeUnit

import com.typesafe.config.{Config, ConfigFactory}
import org.joda.time.DateTime

/**
  * @author avbelyaev
  */
object CloudResource {

  val conf: Config = ConfigFactory.load()
  final val EXCLUDE_TAG_NAME = conf.getString("automator.exclude-tag-name")
  final val EXCLUDE_TAG_VALUE = conf.getString("automator.exclude-tag-value")

  val tableHeader: List[String] =
    List("Runtime, h", "Name", "Type", "IP")
}


// TODO count $ spent
trait CloudResource {

  val excludedFromCheck: Boolean = false
  val resourceType: String
  val launchTime: DateTime
  val name: String
  val ip: String

  def runtimeHours(): Long =
    TimeUnit.MILLISECONDS.toHours(DateTime.now().getMillis - this.launchTime.getMillis)

  def toTableRow: List[String] =
    List(this.runtimeHours().toString, this.name, this.resourceType, this.ip)

  override def toString: String =
    s"${this.runtimeHours()}h ${this.name}, type: ${this.resourceType}, ip: ${this.ip}\n"
}
