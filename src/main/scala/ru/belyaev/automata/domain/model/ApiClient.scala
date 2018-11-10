package ru.belyaev.automata.domain.model

import com.amazonaws.auth._
import com.amazonaws.services.ec2.model.{DescribeInstancesRequest, DescribeVolumesRequest, Filter}
import com.amazonaws.services.ec2.{AmazonEC2, AmazonEC2ClientBuilder}
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


class AwsApiClient(accessKeyId: String,
                   secretAccessKey: String,
                   regionName: String)
  extends ApiClient {

  private val credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey)

  private val ec2: AmazonEC2 = AmazonEC2ClientBuilder.standard()
    .withCredentials(new AWSStaticCredentialsProvider(credentials))
    .withRegion(regionName)
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


class RaxApiClient(username: String,
                   password: String,
                   regionName: String)
  extends ApiClient {

  override def activeInstances(): List[CloudResource] = super.activeInstances()
}