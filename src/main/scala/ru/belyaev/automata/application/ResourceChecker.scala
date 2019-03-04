package ru.belyaev.automata.application

import ru.belyaev.automata.domain.model.cloud.{CloudProvider, CloudResource}
import ru.belyaev.automata.port.adapter.cloud.aws.{AwsProvider, AwsResource, AwsVolume}

/**
  * @author avbelyaev
  */
object ResourceChecker {

  def missingResources(provider: AwsProvider, filter: Filter): List[CloudResource] =
    this.missingResources(provider.asInstanceOf[CloudProvider], filter)
      .union(this.missingVolumes(provider, filter))

  def missingResources(provider: CloudProvider, filter: Filter): List[CloudResource] =
    provider.activeInstances()
      .filter(instance => filter.doFilter(instance))
      .sortBy(instance => instance.runtimeHours())(Ordering[Long].reverse)

  private def missingVolumes(provider: AwsProvider, filter: Filter): List[AwsVolume] =
    provider.activeVolumes()
      .filter(volume => filter.doFilter(volume))
      .sortBy(volume => volume.runtimeHours())(Ordering[Long].reverse)
}


// TODO refactor this sh*t of doFilter,doFilter,doFilter,doFilter
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
