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
}

class AwsResource private(name: String,
                          launchTime: DateTime,
                          descriptor: Descriptor,
                          ip: String,
                          resourceType: String)
  extends CloudResource(name, launchTime) {

  def this(descriptor: Descriptor) = {
    this(AwsResource.nameOfDescr(descriptor), AwsResource.launchTimeOfDescr(descriptor))
  }

}
