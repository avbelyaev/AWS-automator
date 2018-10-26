import java.util

/**
 * @author avbelyaev
 */
object App {

  def main(args: Array[String]): Unit = {

    print("hello my fellow scala!")

    val persons = new util.ArrayList[String]()

    var x = 5
    var y = if (0 == x % 2) 1 else 0

    println(y)

    val xs = List(1, 2, 3, 4, 5)
    val pred: Int => Boolean =
      x => 0 == x % 2

    val multiplier: Int => Double =
      x => x * 2.5

    val ys = filter(pred, multiplier, xs)
    println(ys)

    val fs = List(1, 2, 3, 2, 3, 4, 5)
    println(removeDups(fs))
  }

  def filter(pred: Int => Boolean,
             func: Int => Double,
             xs: List[Int]): List[Double] = {
    if (xs.isEmpty) {
      List.empty

    } else {
      val head :: tail = xs
      if (pred(head)) {
        List(func(head)) ::: filter(pred, func, tail)

      } else {
        filter(pred, func, tail)
      }
    }
  }

  def removeDups(xs: List[Int]): List[Int] = {
    dupHeler(List.empty, xs)
  }

  def dupHeler(noDups: List[Int], xs: List[Int]): List[Int] = {
    if (xs.isEmpty) {
      noDups

    } else {
      val head :: tail = xs
      if (noDups.contains(head)) {
        dupHeler(noDups, tail)

      } else {
        dupHeler(noDups ++ List(head), tail)
      }
    }
  }
}
