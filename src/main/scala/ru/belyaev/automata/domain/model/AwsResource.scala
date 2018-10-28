package ru.belyaev.automata.domain.model

import org.joda.time.DateTime

/**
  * @author avbelyaev
  */
object AwsResource {

  def nameOfDescr(descriptor: Descriptor): String = {
    descriptor.name
  }

  def launchTimeOfDescr(descriptor: Descriptor): DateTime = {
    descriptor.launchTime
  }

  def ip(descriptor: Descriptor): String = "8.8.8.8"

  def resType(descriptor: Descriptor): String = "instance"
}

class AwsResource private(name: String,
                          launchTime: DateTime,
                          descriptor: Descriptor)
  extends CloudResource(name, launchTime) {
  val ip: String = AwsResource.ip(descriptor)
  val resourceType: String = AwsResource.resType(descriptor)

  def this(descriptor: Descriptor) = {
    this(AwsResource.nameOfDescr(descriptor), AwsResource.launchTimeOfDescr(descriptor), descriptor)
  }

}
