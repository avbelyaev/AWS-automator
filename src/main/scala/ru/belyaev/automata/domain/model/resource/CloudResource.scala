package ru.belyaev.automata.domain.model.resource

import java.util.concurrent.TimeUnit

import org.joda.time.DateTime

/**
  * @author avbelyaev
  */
object CloudResourceTags {

  final val CLOUD_HOST_AUTOMATION_TAG_NAME = "cloud-host-automation"
  final val CHECK_EXCLUSION_TAG_VALUE = "exclude"
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

  override def toString: String =
    s"${this.runtimeHours()}h ${this.name}, type: ${this.resourceType}, ip: ${this.ip}"
}
