package ru.belyaev.automata.domain.model.resource

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime
import org.scalatest.FunSuite

/**
  * @author avbelyaev
  */
class AwsResourceTests extends FunSuite {

  test("should create aws resource of descriptor") {
    // given
    val name = "host-1"
    val launchedAt = DateTime.now() - 5.hours
    val descriptor = Descriptor(name, launchedAt, "RUNNING", "t2.micro", "14.88.13.37")

    // when
    val awsResource = new AwsResource(descriptor)

    // then
    assert(awsResource.name == name)
    assert(awsResource.runtimeHours == 5)
  }
}
