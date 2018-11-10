package ru.belyaev.automata.domain.model.resource

import org.joda.time.DateTime

/**
  * @author avbelyaev
  */
trait CloudResource {

  val launchTime: DateTime
  val name: String

  def runtimeHours: Long =
    (DateTime.now().getMillis - this.launchTime.getMillis) / 1000 / 60 / 60

  override def toString: String = s"${this.runtimeHours}h ${this.name}"
}
