package ru.belyaev.automata.domain.model.resource

import com.amazonaws.services.ec2.model.Instance
import org.joda.time.DateTime

/**
  * @author avbelyaev
  */
class RaxInstance(descriptor: Instance)
  extends CloudResource {

  val launchTime: DateTime = DateTime.now()
  val resourceType: String = "t2"
  val state: String = "down"
  val name: String = "rax-no-name"
  val ip: String = "8.8.8.8"

  override def toString: String =
    s"RaxInstance ${super.toString()}, type ${this.resourceType}"
}
