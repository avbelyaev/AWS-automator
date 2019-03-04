package ru.belyaev.automata.port.adapter.cloud.rax

import com.typesafe.config.{Config, ConfigFactory}
import org.jclouds.ContextBuilder
import org.jclouds.openstack.nova.v2_0.NovaApi
import ru.belyaev.automata.domain.model.cloud.{CloudProvider, CloudResource}

import scala.collection.JavaConverters._


/**
  * @author avbelyaev
  */
class RaxProvider extends CloudProvider {

  private val conf: Config = ConfigFactory.load()
  private val username = conf.getString("rax.username")
  private val password = conf.getString("rax.password")
  private val apiKey = conf.getString("rax.apikey")
  private val region = conf.getString("rax.region")

  private val serverApi = ContextBuilder
    .newBuilder("rackspace-cloudservers-us")
    //    .credentialsSupplier(() =>
    //      LoginCredentials.builder().user(username).password(password).build())
    .credentials(username, apiKey)
    .buildApi(classOf[NovaApi])
    .getServerApi(region)

  override def activeInstances(): List[CloudResource] =
    this.serverApi.listInDetail().asScala.toList
      .map(server => new RaxResource(server.get(0)))
}
