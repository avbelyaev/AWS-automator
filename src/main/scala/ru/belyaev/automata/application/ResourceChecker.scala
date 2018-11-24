package ru.belyaev.automata.application

import ru.belyaev.automata.domain.model.{CloudApi, CloudResource}
import ru.belyaev.automata.port.adapter.{AwsApiClient, AwsResource, AwsVolume}

/**
  * @author avbelyaev
  */
// this is more of port-adapter layer tbh
object ResourceChecker {

  def missingResources(provider: AwsApiClient, filter: Filter): List[CloudResource] =
    this.missingInstances(provider, filter)
      .union(this.missingVolumes(provider, filter))

  def missingInstances(provider: CloudApi, filter: Filter): List[CloudResource] =
    provider.activeInstances()
      .filter(instance => filter.doFilter(instance))
      .sortBy(instance => instance.runtimeHours())(Ordering[Long].reverse)

  private def missingVolumes(provider: AwsApiClient, filter: Filter): List[AwsVolume] =
    provider.activeVolumes()
      .filter(instance => filter.doFilter(instance))
      .sortBy(instance => instance.runtimeHours())(Ordering[Long].reverse)
}


class Filter(nameRegex: String, runtimeHoursThreshold: Long) {

  def doFilter(resource: CloudResource): Boolean =
    resource.name.matches(nameRegex) &&
      resource.runtimeHours > runtimeHoursThreshold &&
      !resource.excludedFromCheck

  def doFilter(resource: AwsResource): Boolean =
    this.doFilter(resource.asInstanceOf[CloudResource])

  def doFilter(resource: AwsVolume): Boolean =
    resource.runtimeHours > runtimeHoursThreshold &&
      !resource.selfTerminated

  override def toString: String =
    s"Filter: {name-regex: ${this.nameRegex}, runtime-threshold: ${this.runtimeHoursThreshold}h}"
}