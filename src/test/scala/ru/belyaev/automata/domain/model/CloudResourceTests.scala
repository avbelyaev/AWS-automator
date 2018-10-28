package ru.belyaev.automata.domain.model

import org.joda.time.DateTime
import org.scalatest.FunSuite

import com.github.nscala_time.time.Imports._


/**
  * @author avbelyaev
  */
class CloudResourceTests extends FunSuite {

  test("should count runtime") {
    // given
    val host = new CloudResource("host-1", DateTime.now() - 3.hour)

    // when
    val runtime = host.runtimeHours

    // then
    assert(3 == runtime)
  }
}
