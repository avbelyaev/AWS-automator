package ru.belyaev.automata.domain.model.resource

import com.amazonaws.services.ec2.model.{Instance, Tag}
import org.joda.time.DateTime

import scala.collection.JavaConverters._


/**
  * @author avbelyaev
  */
object AwsResource {
  def nameTag(tags: List[Tag]): String =
    this.extractTag(tags, "Name").getOrElse("n/a")

  def extractTag(tags: List[Tag], tagName: String): Option[String] =
    tags.find(tag => tagName.equalsIgnoreCase(tag.getKey))
      .map(tag => tag.getValue)
}


abstract class AwsResource(descriptor: Instance)
  extends CloudResource {

  // TODO extract onlytags from descriptor
  override lazy val name: String = AwsResource.nameTag(this.descriptor.getTags.asScala.toList)

  val state: String = "n/a"
  val resourceType: String = "n/a"

}


class AwsInstance(descriptor: Instance)
  extends AwsResource(descriptor) {

  override lazy val launchTime: DateTime = new DateTime(descriptor.getLaunchTime)
  override val state: String = descriptor.getState.getName
  override val resourceType: String = descriptor.getInstanceType

  override def toString: String =
    s"Instance ${super.toString}, type: ${this.resourceType}"
}


//class AwsVolume private (descriptor: Volume,
//                         launchTime: DateTime)
//  extends AwsResource(descriptor, launchTime) {
//
//  override val state: String = descriptor.getState
//  override val resourceType: String = descriptor.getVolumeType
//
//  def this(descriptor: Volume) = {
//    this(descriptor, descriptor.getCreateTime)
//  }
//
//  override def toString: String = s"Volume ${super.toString}"
//}