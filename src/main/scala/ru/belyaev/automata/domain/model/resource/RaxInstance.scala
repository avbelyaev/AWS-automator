package ru.belyaev.automata.domain.model.resource

import org.joda.time.DateTime

/**
  * @author avbelyaev
  */
class RaxInstance private(name: String,
                          launchTime: DateTime,
                          descriptor: Descriptor)
  extends CloudResource(name: String, launchTime: DateTime) {

  val resourceType: String = descriptor.resourceType
  val state: String = descriptor.state
  val ip: String = descriptor.ip

  def this(descriptor: Descriptor) {
    this(descriptor.name, descriptor.launchTime, descriptor)
  }

  override def toString: String =
    s"RaxInstance ${super.toString()}, type ${this.resourceType}"
}
