package ru.belyaev.automata.domain.model.resource

import com.amazonaws.services.ec2.model.Instance
import org.scalatest.FunSuite

/**
  * @author avbelyaev
  */
class AwsResourceTests extends FunSuite {

  test("should create aws resource of descriptor") {
    // given
    val instance = new Instance()

    // when
    val awsResource = new AwsResource(instance)

    // then
    //    assert(awsResource.name == name)
    assert(awsResource.runtimeHours == 5)
  }
}
