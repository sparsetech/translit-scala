package translit

import org.scalatest.FunSuite

class RussianSpec extends FunSuite {
  val words = List(
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
    "съел" -> "s`el",
    "тележка" -> "telezhka",
    "ухват" -> "ukhvat",
    "фольклор" -> "fol'klor",
    "халтура" -> "khaltura",
    "цвет" -> "cvet",
    "червь" -> "cherv'",
    "швея" -> "shveya",
    "щавель" -> "shchavel'",
    "электровоз" -> "yelektrovoz",
    "юла" -> "yula",
    "ягненок" -> "yagnenok"
  )

  words.foreach { case (cyrillic, latin) =>
    test(s"$cyrillic <-> $latin") {
      assert(Russian.cyrillicToLatin(cyrillic) == latin)
      assert(Russian.latinToCyrillic(latin) == cyrillic)

      assert(Russian.cyrillicToLatin(cyrillic.toUpperCase) == latin.toUpperCase)
      assert(Russian.latinToCyrillic(latin.toUpperCase) == cyrillic.toUpperCase)
    }
  }

  test("Other words") {
    assert(Russian.latinToCyrillic("peshkom" ) == "пешком")
    assert(Russian.latinToCyrillic("zhizn'"  ) == "жизнь")
    assert(Russian.latinToCyrillic("shchetka") == "щетка")
    assert(Russian.latinToCyrillic("rajon") == "район")
    assert(Russian.latinToCyrillic("schitayu") == "считаю")
    assert(Russian.latinToCyrillic("proiskhodit'") == "происходить")
    assert(Russian.latinToCyrillic("shirokoe") == "широкое")
    assert(Russian.latinToCyrillic("carivshij") == "царивший")
    assert(Russian.latinToCyrillic("polusharii") == "полушарии")
  }

  test("ye exceptions") {
    // In this context, ye should not be mapped onto э
    assert(Russian.latinToCyrillic("krasivye") == "красивые")
    assert(Russian.latinToCyrillic("mezhdunarodnye") == "международные")
    assert(Russian.latinToCyrillic("nekotorye") == "некоторые")
    assert(Russian.latinToCyrillic("vyhodnye") == "выходные")
  }

  test("ya") {
    assert(Russian.latinToCyrillic("altaryah") == "алтарях")
    assert(Russian.latinToCyrillic("myagko") == "мягко")
    assert(Russian.latinToCyrillic("ukrasheniya") == "украшения")
  }

  test("yu") {
    assert(Russian.latinToCyrillic("molyu") == "молю")
    assert(Russian.latinToCyrillic("nablyudaem") == "наблюдаем")
  }

  ignore("yu (exception)") {
    // TODO Not covered yet
    assert(Russian.latinToCyrillic("vyuchil") == "выучил")
  }

  ignore("ye") {
    // TODO Foreign words conflict with some rules
    assert(Russian.latinToCyrillic("ryeggi") == "рэгги")
    assert(Russian.latinToCyrillic("myenskim") == "мэнским")
    assert(Russian.latinToCyrillic("Gryemom") == "Грэмом")
    assert(Russian.latinToCyrillic("Bryegg") == "Брэгг")
    assert(Russian.latinToCyrillic("Revyu") == "Ревю")
    assert(Russian.latinToCyrillic("Umyeo") == "Умэко")
  }

  test("Restore case") {
    assert(Russian.latinToCyrillic("ZHIZN'") == "ЖИЗНЬ")
    assert(Russian.latinToCyrillic("LOZH'") == "ЛОЖЬ")
    assert(Russian.latinToCyrillic("FLAG`") == "ФЛАГЪ")
    assert(Russian.latinToCyrillic("Ya.Shmid`") == "Я.Шмидъ")
  }

  test("Incremental transliteration") {
    assert(Russian.latinToCyrillic("vy") == "вы")
    assert(Russian.latinToCyrillic("S'") == "Сь")
    assert(Russian.latinToCyrillic("S'o") == "Сьо")
    assert(Russian.latinToCyrillic("S'O") == "СЬО")
  }

  test("Cyrillic to Latin") {
    val latin = Russian.cyrillicToLatin("Фердинанд Теннис, описал два важнейших социологических абстрактных понятия")
    assert(latin == "Ferdinand Tennis, opisal dva vazhnejshikh sociologicheskikh abstraktnykh ponyatiya")

    val latin2 = Russian.cyrillicToLatin("Звезда расположена в главной части созвездия приблизительно посередине между Гаммой Лебедя и Альбирео.")
    assert(latin2 == "Zvezda raspolozhena v glavnoj chasti sozvezdiya priblizitel'no poseredine mezhdu Gammoj Lebedya i Al'bireo.")
  }
}
