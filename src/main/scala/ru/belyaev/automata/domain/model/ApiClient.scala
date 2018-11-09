package ru.belyaev.automata.domain.model

import com.amazonaws.auth._
import com.amazonaws.services.ec2.model.{DescribeInstancesRequest, DescribeVolumesRequest, Filter}
import com.amazonaws.services.ec2.{AmazonEC2, AmazonEC2ClientBuilder}
import ru.belyaev.automata.domain.model.resource.AwsResource

import scala.collection.JavaConverters._

/**
  * @author avbelyaev
  */
trait ApiClient {
  def activeInstances(): List[AwsResource] = List.empty

  def activeVolumes(): List[AwsResource] = List.empty
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


  override def activeInstances(): List[AwsResource] = {
    val request = new DescribeInstancesRequest()
      .withFilters(AwsApiClient.runningInstanceFilter)
    this.ec2.describeInstances(request)
      .getReservations.asScala.head
      .getInstances.asScala
      .map(instance => new AwsResource(instance))
      .toList
  }

  override def activeVolumes(): List[AwsResource] = {
    val request = new DescribeVolumesRequest()
      .withFilters(AwsApiClient.runningVolumeFilter)
    this.ec2.describeVolumes(request)
      .getVolumes.asScala
      .map(volume => new AwsResource(volume))
      .toList
  }
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


class RaxApiClient(username: String,
                   password: String,
                   regionName: String)
  extends ApiClient {

  private val client = null

  override def activeInstances(): List[AwsResource] = super.activeInstances()
}