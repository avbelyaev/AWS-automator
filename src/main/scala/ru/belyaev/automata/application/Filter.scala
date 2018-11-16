package ru.belyaev.automata.application

import ru.belyaev.automata.domain.model.CloudResource
import ru.belyaev.automata.port.adapter.resource.{AwsResource, AwsVolume}

/**
  * @author avbelyaev
  */
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
