package ru.belyaev.automata.domain.model

import org.joda.time.DateTime
import org.scalatest.FunSuite
import com.github.nscala_time.time.Imports._

/**
  * @author avbelyaev
  */
class AwsResourceTests extends FunSuite {

  test("should create aws resource of descriptor") {
    // given
    val name = "host-1"
    val launchedAt = DateTime.now() - 5.hours
    val descriptor = Descriptor(name, launchedAt)

    // when
    val awsResource = new AwsResource(descriptor)

    // then
    assert(awsResource.name == name)
    assert(awsResource.runtimeHours == 5)
  }
}
