package ru.belyaev.automata.domain.model.resource

import org.joda.time.DateTime

/**
  * @author avbelyaev
  */
trait CloudResource {

  // when initializing C in (A extends B extends C), it's fields are null
  // why? they should be initialized only once so at this point they are null.
  // when A is initialized, fields become not null. lazy
  lazy val launchTime: DateTime = DateTime.now()
  lazy val name: String = "n/a"

  val runtimeHours: Long =
    (DateTime.now().getMillis - this.launchTime.getMillis) / 1000 / 60 / 60

  override def toString: String = s"${this.runtimeHours}h ${this.name}"
}
