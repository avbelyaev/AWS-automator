package ru.belyaev.automata.domain.model.resource

import com.amazonaws.services.ec2.model.{Instance, Tag, Volume}
import org.joda.time.DateTime

import scala.collection.JavaConverters._


/**
  * @author avbelyaev
  */
object AwsResource {
  def nameTag(tags: List[Tag]): Option[String] =
    this.extractTag(tags, "Name")

  def extractTag(tags: List[Tag], tagName: String): Option[String] =
    tags.find(tag => tagName.equalsIgnoreCase(tag.getKey))
      .map(tag => tag.getValue)
}


abstract class AwsResource(descriptor: Either[Instance, Volume])
  extends CloudResource {

  private val tags = descriptor match {
    case Left(value) => value.getTags
    case Right(value) => value.getTags
  }
  protected val nameTag: Option[String] = AwsResource.nameTag(tags.asScala.toList)

  val resourceType: String
  val state: String
}


class AwsInstance(descriptor: Instance)
  extends AwsResource(Left(descriptor)) {

  override val name: String = this.nameTag.getOrElse(descriptor.getInstanceId)
  override val launchTime: DateTime = new DateTime(descriptor.getLaunchTime)
  override val state: String = descriptor.getState.getName
  override val resourceType: String = descriptor.getInstanceType

  override def toString: String =
    s"Instance ${super.toString}, type: ${this.resourceType}"
}


class AwsVolume(descriptor: Volume)
  extends AwsResource(Right(descriptor)) {

  override val name: String = this.nameTag.getOrElse(descriptor.getVolumeId)
  override val launchTime: DateTime = new DateTime(descriptor.getCreateTime)
  override val state: String = descriptor.getState
  override val resourceType: String = descriptor.getVolumeType

  override def toString: String = s"Volume ${super.toString}"
}