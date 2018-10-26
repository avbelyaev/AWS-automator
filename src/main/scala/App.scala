import java.util

object App {

  def main(args: Array[String]): Unit = {

    print("hello my fellow scala!")

    val persons = new util.ArrayList[String]()

    var x = 5
    var y = if (0 == x % 2) 1 else 0

    print(y)


//    val fact: Int => Int
//        fact
//      x => if (0 == x) 1 else 1 * fact(x - 1)

//    print(s"${fact(5)}")


  }
}
