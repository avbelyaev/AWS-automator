import org.scalatest.{FlatSpec, FunSuite}

/**
  * @author avbelyaev
  */
class AppTests extends FunSuite {

  test("filter should filter values") {
    // given
    val values = List(1, 2, 3, 4, 5, 6)
    val evenNumbers: Int => Boolean =
      n => 0 == n % 2
    val id: Int => Double =
      x => x

    // when
    val filtered = App.filter(evenNumbers, id, values)

    // then
    assert(filtered === List(2, 4, 6))
  }
}
