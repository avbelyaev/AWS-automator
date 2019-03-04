package ru.belyaev.automata.port.adapter.cloud.aws

import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.ec2.model.{DescribeInstancesRequest, DescribeVolumesRequest, Filter}
import com.amazonaws.services.ec2.{AmazonEC2, AmazonEC2ClientBuilder}
import com.typesafe.config.{Config, ConfigFactory}
import ru.belyaev.automata.domain.model.cloud.CloudProvider

import scala.collection.JavaConverters._


/**
  * @author avbelyaev
  */
object AwsClient {

  val runningInstanceFilter: Filter =
    awsFilter("instance-state-name", "running", "pending")

  val runningVolumeFilter: Filter =
    awsFilter("status", "creating", "available", "error", "in-use")

  def awsFilter(name: String, values: String*): Filter =
    new Filter()
      .withName(name)
      .withValues(values: _*)
}

class AwsProvider extends CloudProvider {

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
      .withFilters(AwsClient.runningInstanceFilter)
    this.ec2.describeInstances(request)
      .getReservations.asScala
      .map(reservation => new AwsInstance(reservation.getInstances.asScala.head))
      .toList
  }

  override def activeVolumes(): List[AwsVolume] = {
    val request = new DescribeVolumesRequest()
      .withFilters(AwsClient.runningVolumeFilter)
    this.ec2.describeVolumes(request)
      .getVolumes.asScala
      .map(volume => new AwsVolume(volume))
      .toList
  }
}
