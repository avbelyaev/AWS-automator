package ru.belyaev.automata.domain.model.resource

import org.joda.time.DateTime

/**
  * @author avbelyaev
  */
case class Descriptor(name: String,
                      launchTime: DateTime,
                      state: String,
                      resourceType: String,
                      ip: String) {
  // empty
}
