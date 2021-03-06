package ru.belyaev.automata.port.adapter.cloud.rax

import org.jclouds.openstack.nova.v2_0.domain.Server
import org.joda.time.DateTime
import ru.belyaev.automata.domain.model.cloud.CloudResource

import scala.collection.JavaConverters._

/**
  * @author avbelyaev
  */
class RaxResource(descriptor: Server)
  extends CloudResource {

  val launchTime: DateTime = new DateTime(descriptor.getCreated)
  val resourceType: String = descriptor.getFlavor.getId
  val state: String = descriptor.getStatus.value()
  val name: String = descriptor.getName
  val ip: String = descriptor.getAddresses.get("RC-DMZ").asScala.toList.head.getAddr

  override def toString: String = s"RaxInstance ${super.toString}"
}
