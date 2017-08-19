package translit

import org.scalatest.FunSuite

class UkrainianSpec extends FunSuite {
  val correctMapping = List(
    "Алушта" -> "Alushta",
    "Андрій" -> "Andrii",
    "Борщагівка" -> "Borshchahivka",
    "Борисенко" -> "Borysenko",
    "Вінниця" -> "Vinnytsia",
    "Володимир" -> "Volodymyr",
    "Гадяч" -> "Hadiach",
    "Богдан" -> "Bohdan",
    "Згурський" -> "Zghurskyi",
    "Ґалаґан" -> "Galagan",
    "Ґорґани" -> "Gorgany",
    "Донецьк" -> "Donetsk",
    "Дмитро" -> "Dmytro",
    "Рівне" -> "Rivne",
    "Олег" -> "Oleh",
    "Есмань" -> "Esman",
    "Єнакієве" -> "Yenakiieve",
    "Гаєвич" -> "Haievych",
    "Короп'є" -> "Koropie",
    "Житомир" -> "Zhytomyr",
    "Жанна" -> "Zhanna",
    "Жежелів" -> "Zhezheliv",
    "Закарпаття" -> "Zakarpattia",
    "Казимирчук" -> "Kazymyrchuk",
    "Медвин" -> "Medvyn",
    "Михайленко" -> "Mykhailenko",
    "Іванків" -> "Ivankiv",
    "Іващенко" -> "Ivashchenko",
    "Їжакевич" -> "Yizhakevych",
    "Кадиївка" -> "Kadyivka",
    "Йосипівка" -> "Yosypivka",
    "Стрий" -> "Stryi",
    "Олексій" -> "Oleksii",
    "Київ" -> "Kyiv",
    "Коваленко" -> "Kovalenko",
    "Лебедин" -> "Lebedyn",
    "Леонід" -> "Leonid",
    "Миколаїв" -> "Mykolaiv",
    "Маринич" -> "Marynych",
    "Ніжин" -> "Nizhyn",
    "Наталія" -> "Nataliia",
    "Одеса" -> "Odesa",
    "Онищенко" -> "Onyshchenko",
    "Полтава" -> "Poltava",
    "Петро" -> "Petro",
    "Решетилівка" -> "Reshetylivka",
    "Рибчинський" -> "Rybchynskyi",
    "Суми" -> "Sumy",
    "Соломія" -> "Solomiia",
    "Тернопіль" -> "Ternopil",
    "Троць" -> "Trots",
    "Ужгород" -> "Uzhhorod",
    "Уляна" -> "Uliana",
    "Фастів" -> "Fastiv",
    "Філіпчук" -> "Filipchuk",
    "Харків" -> "Kharkiv",
    "Христина" -> "Khrystyna",
    "Біла Церква" -> "Bila Tserkva",
    "Стеценко" -> "Stetsenko",
    "Чернівці" -> "Chernivtsi",
    "Шевченко" -> "Shevchenko",
    "Шостка" -> "Shostka",
    "Кишеньки" -> "Kyshenky",
    "Щербухи" -> "Shcherbukhy",
    "Гоща" -> "Hoshcha",
    "Гаращенко" -> "Harashchenko",
    "Юрій" -> "Yurii",
    "Корюківка" -> "Koriukivka",
    "Яготин" -> "Yahotyn",
    "Ярошенко" -> "Yaroshenko",
    "Костянтин" -> "Kostiantyn",
    "Знам'янка" -> "Znamianka",
    "Феодосія" -> "Feodosiia"
  )

  def removeApostropheAndSoftSign(str: String): String =
    str
      .replaceAll("ь", "")
      .replaceAll("'", "")

  correctMapping.foreach { case (cyrillic, latin) =>
    test(s"$latin -> $cyrillic") {
      assert(Ukrainian.latinToCyrillic(latin) ==
        removeApostropheAndSoftSign(cyrillic))
    }
  }

  test("yi") {
    assert(Ukrainian.latinToCyrillic("Kyiv") == "Київ")
    assert(Ukrainian.latinToCyrillic("Stryiskyi park") ==
      removeApostropheAndSoftSign("Стрийський парк"))
    assert(Ukrainian.latinToCyrillic("Stryiska") ==
      removeApostropheAndSoftSign("Стрийська"))
  }

  test("Examples") {
    assert(Ukrainian.latinToCyrillic("Ukrainskyi") == removeApostropheAndSoftSign("Український"))
    assert(Ukrainian.latinToCyrillic("Yaroshenko") == removeApostropheAndSoftSign("Ярошенко"))
    assert(Ukrainian.latinToCyrillic("Znamianka") == removeApostropheAndSoftSign("Знам'янка"))
    assert(Ukrainian.latinToCyrillic("pirania") == removeApostropheAndSoftSign("піранья"))
    assert(Ukrainian.latinToCyrillic("kurier") == removeApostropheAndSoftSign("кур'єр"))
  }

  test("Case sensitivity") {
    assert(Ukrainian.latinToCyrillic("Mariana") == removeApostropheAndSoftSign("Мар'яна"))
    assert(Ukrainian.latinToCyrillic("MARIANA") == removeApostropheAndSoftSign("Мар'яна").toUpperCase)

    assert(Ukrainian.latinToCyrillic("Yaroslava") == "Ярослава")
    assert(Ukrainian.latinToCyrillic("YAROSLAVA") == "Ярослава".toUpperCase)

    assert(Ukrainian.latinToCyrillic("Myroslava") == "Мирослава")
    assert(Ukrainian.latinToCyrillic("MYROSLAVA") == "Мирослава".toUpperCase)
  }

  test("Drop soft signs and apostrophes") {
    assert(Ukrainian.latinToCyrillic("imia") == removeApostropheAndSoftSign("ім'я"))
    assert(Ukrainian.latinToCyrillic("Miakyi") == removeApostropheAndSoftSign("М'який"))
    assert(Ukrainian.latinToCyrillic("obiekt") == removeApostropheAndSoftSign("об'єкт"))
    assert(Ukrainian.latinToCyrillic("piat") == removeApostropheAndSoftSign("п'ять"))
    assert(Ukrainian.latinToCyrillic("pamiataty") == removeApostropheAndSoftSign("пам'ятати"))
  }
}
