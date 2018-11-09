package ru.belyaev.automata.domain.model

import ru.belyaev.automata.domain.model.resource.AwsResource

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

  private val client = null

  override def activeInstances(): List[AwsResource] = super.activeInstances()

  override def activeVolumes(): List[AwsResource] = super.activeVolumes()
}


class RaxApiClient(username: String,
                   password: String,
                   regionName: String)
  extends ApiClient {

  private val client = null

  override def activeInstances(): List[AwsResource] = super.activeInstances()
}