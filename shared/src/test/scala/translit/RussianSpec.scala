package translit

import org.scalatest.FunSuite

class RussianSpec extends FunSuite {
  val correctMapping = List(
    "Андрей" -> "Andrej",
    "Борис" -> "Boris",
    "Валера" -> "Valera",
    "гвоздь" -> "gvozd'",
    "днище" -> "dnishhe",
    "Емеля" -> "Emelya",
    "ёлка" -> "yolka",
    "ёлка" -> "jolka",
    "железо" -> "zhelezo",
    "зыбь" -> "zyb'",
    "Ильин" -> "Il'in",
    "Йемен" -> "Jemen",
    "киянка" -> "kiyanka",
    "лещ" -> "leshh",
    "мышьяк" -> "mysh'yak",
    "Новгород" -> "Novgorod",
    "овраг" -> "ovrag",
    "пьянство" -> "p'yanstvo",
    "роща" -> "roshha",
    "съел" -> "s#el",
    "тележка" -> "telezhka",
    "ухват" -> "uxvat",
    "ухват" -> "uhvat",
    "фольклор" -> "fol'klor",
    "халтура" -> "haltura",
    "цвет" -> "cvet",
    "червь" -> "cherv'",
    "швея" -> "shveya",
    "щавель" -> "shhavel'",
    "электровоз" -> "yelektrovoz",
    "юла" -> "yula",
    "ягненок" -> "yagnenok",
  )

  def removeApostropheAndSoftSign(str: String): String =
    str
      .replaceAll("ь", "")
      .replaceAll("'", "")

  correctMapping.foreach {
    case (cyrillic, latin) =>
      test(s"$latin -> $cyrillic") {
        assert(
          Russian.latinToCyrillic(latin) ==
            cyrillic)
      }
  }
}
