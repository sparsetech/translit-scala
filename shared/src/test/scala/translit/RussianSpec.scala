package translit

import org.scalatest.FunSuite

class RussianSpec extends FunSuite {
  val correctMapping = List(
    "Андрей" -> "Andrej",
    "Борис" -> "Boris",
    "Валера" -> "Valera",
    "гвоздь" -> "gvozd'",
    "днище" -> "dnishche",
    "Емеля" -> "Emelya",
    "ёлка" -> "yolka",
    "железо" -> "zhelezo",
    "зыбь" -> "zyb'",
    "Ильин" -> "Il'in",
    "Йемен" -> "Jemen",
    "киянка" -> "kiyanka",
    "лещ" -> "leshch",
    "мышьяк" -> "mysh'yak",
    "Новгород" -> "Novgorod",
    "овраг" -> "ovrag",
    "пьянство" -> "p'yanstvo",
    "роща" -> "roshcha",
    "съел" -> "s\"el",
    "тележка" -> "telezhka",
    "ухват" -> "uxvat",
    "ухват" -> "uhvat",
    "фольклор" -> "fol'klor",
    "халтура" -> "haltura",
    "цвет" -> "cvet",
    "червь" -> "cherv'",
    "швея" -> "shveya",
    "щавель" -> "shchavel'",
    "электровоз" -> "yelektrovoz",
    "юла" -> "yula",
    "ягненок" -> "yagnenok",
  )

  correctMapping.foreach {
    case (cyrillic, latin) =>
      test(s"$latin -> $cyrillic") {
        assert(
          Russian.latinToCyrillic(latin) ==
            cyrillic)
      }
  }
}
