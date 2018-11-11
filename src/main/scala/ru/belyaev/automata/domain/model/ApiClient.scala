package ru.belyaev.automata.domain.model

import com.amazonaws.auth._
import com.amazonaws.services.ec2.model.{DescribeInstancesRequest, DescribeVolumesRequest, Filter}
import com.amazonaws.services.ec2.{AmazonEC2, AmazonEC2ClientBuilder}
import com.typesafe.config.{Config, ConfigFactory}
import ru.belyaev.automata.domain.model.resource.{AwsInstance, AwsVolume, CloudResource}

import scala.collection.JavaConverters._

/**
  * @author avbelyaev
  */
trait ApiClient {

  def activeInstances(): List[CloudResource] = List.empty

  def activeVolumes(): List[CloudResource] = List.empty
}


object AwsApiClient {

  val runningInstanceFilter: Filter =
    awsFilter("instance-state-name", "running", "pending")

  val runningVolumeFilter: Filter =
    awsFilter("status", "creating", "available", "error", "in-use")

  def awsFilter(name: String, values: String*): Filter =
    new Filter()
      .withName(name)
      .withValues(values: _*)
}


class AwsApiClient extends ApiClient {

  private val conf: Config = ConfigFactory.load()
  private val accessKey = conf.getString("aws.access-key")
  private val secret = conf.getString("aws.secret")
  private val region = conf.getString("aws.region")

  private val credentials = new BasicAWSCredentials(accessKey, secret)

  private val ec2: AmazonEC2 = AmazonEC2ClientBuilder.standard()
    .withCredentials(new AWSStaticCredentialsProvider(credentials))
    .withRegion(region)
    .build()


  override def activeInstances(): List[AwsInstance] = {
    val request = new DescribeInstancesRequest()
      .withFilters(AwsApiClient.runningInstanceFilter)
    this.ec2.describeInstances(request)
      .getReservations.asScala.head
      .getInstances.asScala
      .map(instance => new AwsInstance(instance))
      .toList
  }

  override def activeVolumes(): List[AwsVolume] = {
    val request = new DescribeVolumesRequest()
      .withFilters(AwsApiClient.runningVolumeFilter)
    this.ec2.describeVolumes(request)
      .getVolumes.asScala
      .map(volume => new AwsVolume(volume))
      .toList
  }
}


class RaxApiClient extends ApiClient {

  private val conf: Config = ConfigFactory.load()
  private val username = conf.getString("rax.username")
  private val password = conf.getString("rax.password")
  private val region = conf.getString("rax.region")

  override def activeInstances(): List[CloudResource] = super.activeInstances()
}