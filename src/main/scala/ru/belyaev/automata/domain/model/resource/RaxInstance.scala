package ru.belyaev.automata.domain.model.resource

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
}
