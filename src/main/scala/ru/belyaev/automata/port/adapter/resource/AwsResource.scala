package ru.belyaev.automata.port.adapter.resource

import com.amazonaws.services.ec2.model.{Instance, Tag, Volume}
import org.joda.time.DateTime
import ru.belyaev.automata.domain.model.CloudResource
import ru.belyaev.automata.domain.model.CloudResourceTags.{EXCLUDE_TAG_NAME, EXCLUDE_TAG_VALUE}

import scala.collection.JavaConverters._


/**
  * @author avbelyaev
  */
object AwsResource {

  def excludedFromCheck(tags: List[Tag]): Boolean =
    extractTag(tags, EXCLUDE_TAG_NAME) match {
      case None => false
      case Some(value) => EXCLUDE_TAG_VALUE.equalsIgnoreCase(value)
    }

  def nameTag(tags: List[Tag]): Option[String] =
    this.extractTag(tags, "Name")

  def extractTag(tags: List[Tag], tagName: String): Option[String] =
    tags.find(tag => tagName.equalsIgnoreCase(tag.getKey))
      .map(tag => tag.getValue)
}


abstract class AwsResource(descriptor: Either[Instance, Volume])
  extends CloudResource {

  private val tags = descriptor match {
    case Left(value) => value.getTags.asScala.toList
    case Right(value) => value.getTags.asScala.toList
  }

  // cloud resource
  override val excludedFromCheck: Boolean = AwsResource.excludedFromCheck(tags)

  val state: String

  protected val nameTag: Option[String] = AwsResource.nameTag(tags)

}


class AwsInstance(descriptor: Instance)
  extends AwsResource(Left(descriptor)) {

  // cloud resource
  val resourceType: String = descriptor.getInstanceType
  val launchTime: DateTime = new DateTime(descriptor.getLaunchTime)
  val name: String = this.nameTag.getOrElse(descriptor.getInstanceId)
  val ip: String = descriptor.getPublicIpAddress

  // aws resource
  val state: String = descriptor.getState.getName

  override def toString: String = s"Instance ${super.toString}"
}


class AwsVolume(descriptor: Volume)
  extends AwsResource(Right(descriptor)) {

  // cloud resource
  val resourceType: String = descriptor.getVolumeType
  val launchTime: DateTime = new DateTime(descriptor.getCreateTime)
  val name: String = this.nameTag.getOrElse(descriptor.getVolumeId)
  val ip: String = "n/a"

  // aws resource
  val state: String = descriptor.getState

  val selfTerminated: Boolean = descriptor.getAttachments.asScala.toList
    .exists(attachment => attachment.getDeleteOnTermination)

  override def toString: String = s"Volume ${super.toString}"
}