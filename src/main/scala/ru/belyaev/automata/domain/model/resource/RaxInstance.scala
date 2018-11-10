package ru.belyaev.automata.domain.model.resource

import org.joda.time.DateTime

/**
  * @author avbelyaev
  */
class RaxInstance(descriptor: Descriptor)
  extends CloudResource {

  val resourceType: String = descriptor.resourceType
  val state: String = descriptor.state
  val ip: String = descriptor.ip

  override def toString: String =
    s"RaxInstance ${super.toString()}, type ${this.resourceType}"

  override val launchTime: DateTime = DateTime.now()
  override val name: String = "rax-no-name"
}
