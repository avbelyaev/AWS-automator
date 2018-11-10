package ru.belyaev.automata.application

import ru.belyaev.automata.domain.model.resource.{AwsVolume, CloudResource}

/**
  * @author avbelyaev
  */
class Filter(nameRegex: String, runtimeHoursThreshold: Long) {

  def doFilter(resource: CloudResource): Boolean =
    resource.name.matches(nameRegex) &&
      resource.runtimeHours > runtimeHoursThreshold

  def doFilter(resource: AwsVolume): Boolean =
    resource.runtimeHours > runtimeHoursThreshold //&& !resource.selfTerminated
}