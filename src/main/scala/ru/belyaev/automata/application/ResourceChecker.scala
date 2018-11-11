package ru.belyaev.automata.application

import ru.belyaev.automata.domain.model.resource.{AwsVolume, CloudResource}
import ru.belyaev.automata.domain.model.{ApiClient, AwsApiClient}

/**
  * @author avbelyaev
  */
object ResourceChecker {

  def missingResources(provider: AwsApiClient, filter: Filter): List[CloudResource] =
    this.missingInstances(provider, filter)
      .union(this.missingVolumes(provider, filter))

  def missingInstances(provider: ApiClient, filter: Filter): List[CloudResource] =
    provider.activeInstances()
      .filter(instance => filter.doFilter(instance))
      .sortBy(instance => instance.runtimeHours())(Ordering[Long].reverse)

  private def missingVolumes(provider: AwsApiClient, filter: Filter): List[AwsVolume] =
    provider.activeVolumes()
      .filter(instance => filter.doFilter(instance))
      .sortBy(instance => instance.runtimeHours())(Ordering[Long].reverse)
}
