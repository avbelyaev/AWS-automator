package ru.belyaev.automata.domain.model

import org.joda.time.DateTime

/**
  * @author avbelyaev
  */
class CloudResource(val name: String, launchTime: DateTime) {

  val runtimeHours: Long =
    (DateTime.now().getMillis - this.launchTime.getMillis) / 1000 / 60 / 60

  override def toString: String = s"${this.runtimeHours}h \t${this.name}"
}
