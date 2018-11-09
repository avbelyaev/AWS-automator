package ru.belyaev.automata.domain.model.resource

import com.amazonaws.services.ec2.model.{Instance, Volume}
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

  def this(descriptor: Instance) = {
    //    val launchTime = descriptor.getLaunchTime
    this("asd", DateTime.now(), null)
  }

  def this(descriptor: Volume) = {
    this("asdads", DateTime.now(), null)
  }

  override def toString: String =
    s"AwsResource ${super.toString()}, type ${this.resourceType}"
}
