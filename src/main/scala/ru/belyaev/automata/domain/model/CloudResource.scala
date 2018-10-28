package ru.belyaev.automata.domain.model

import org.joda.time.DateTime

/**
  * @author avbelyaevwdas
  */
class CloudResource(name: String, launchTime: DateTime) {

  def runtimeHours(): Long =
    (DateTime.now().getMillis - this.launchTime.getMillis) / 1000 / 60 / 60

  override def toString: String = s"${this.runtimeHours()}h \t${this.name}"
}
