package ru.belyaev.automata

import ru.belyaev.automata.domain.model.AwsApiClient

/**
  * @author avbelyaev
  */
object CloudHostAutomator {

  def main(arg: Array[String]): Unit = {

    println("starting")

    val region = "eu-west-3"
    val client = new AwsApiClient(accessKey, secret, region)

    val instances = client.activeInstances()
    println(instances)

    val volumes = client.activeVolumes()
    println(volumes)
  }
}
