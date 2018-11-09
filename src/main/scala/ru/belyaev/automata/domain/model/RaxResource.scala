package ru.belyaev.automata.domain.model

import org.joda.time.DateTime

/**
  * @author avbelyaev
  */
class RaxResource private(name: String,
                          launchTime: DateTime,
                          descriptor: Descriptor)
  extends CloudResource(name: String, launchTime: DateTime) {

  val resourceType: String = descriptor.resourceType
  val state: String = descriptor.state
  val ip: String = descriptor.ip

  def this(descriptor: Descriptor) {
    this(descriptor.name, descriptor.launchTime)
  }
}
