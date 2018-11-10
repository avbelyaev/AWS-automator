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


class AwsResource private(descriptor: Instance, // can be of type instance/volume
                          name: String,
                          launchTime: DateTime)
  extends CloudResource(name, launchTime) {

  // TODO extract onlytags from descriptor
  val state: String = "n/a"
  val resourceType: String = "n/a"

  def this(descriptor: Instance, launchTime: DateTime) = {
    //    val launchTime = descriptor.getLaunchTime
    this(descriptor, AwsResource.nameTag(this.descriptor.getTags.asScala.toList), launchTime)
  }

  //  def this(descriptor: Volume, launchTime: DateTime) = {
  //    this("asdads", DateTime.now(), null)
  //  }
}


class AwsInstance private(descriptor: Instance,
                          launchTime: DateTime)
  extends AwsResource(descriptor, launchTime) {

  override val state: String = descriptor.getState.getName
  override val resourceType: String = descriptor.getInstanceType

  def this(descriptor: Instance) = {
    this(descriptor, new DateTime(descriptor.getLaunchTime))
  }

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