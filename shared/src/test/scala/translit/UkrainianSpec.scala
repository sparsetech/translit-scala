package translit

import org.scalatest.FunSuite

class UkrainianSpec extends FunSuite {
  val correctMapping = List(
    "Алушта" -> "Alushta",
    "Андрій" -> "Andriy",
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
    "Кадиївка" -> "Kadyjivka",
    "Йосипівка" -> "Yosypivka",
    "Стрий" -> "Stryi",
    "Олексій" -> "Oleksiy",
    "Київ" -> "Kyjiv",
    "Коваленко" -> "Kovalenko",
    "Лебедин" -> "Lebedyn",
    "Леонід" -> "Leonid",
    "Миколаїв" -> "Mykolajiv",
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
    "Юрій" -> "Yuriy",
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

  test("yi / ji") {
    assert(Ukrainian.latinToCyrillic("Kyjiv") == "Київ")
    assert(Ukrainian.latinToCyrillic("Kryjivka") == "Криївка")
    assert(Ukrainian.latinToCyrillic("Katehoriji") == "Категорії")

    assert(Ukrainian.latinToCyrillic("Stryis'kyi park") == "Стрийський парк")
    assert(Ukrainian.latinToCyrillic("Stryis'ka") == "Стрийська")
    assert(Ukrainian.latinToCyrillic("kofejin") == "кофеїн")
    assert(Ukrainian.latinToCyrillic("pryiniatykh") == "прийнятих")
    assert(Ukrainian.latinToCyrillic("Staryi") == "Старий")
    assert(Ukrainian.latinToCyrillic("Avtomyika") == "Автомийка")
  }

  test("Case sensitivity") {
    assert(Ukrainian.latinToCyrillic("Mar'iana") == "Мар'яна")
    assert(Ukrainian.latinToCyrillic("MAR'IANA") == "Мар'яна".toUpperCase)

    assert(Ukrainian.latinToCyrillic("Yaroslava") == "Ярослава")
    assert(Ukrainian.latinToCyrillic("YAROSLAVA") == "Ярослава".toUpperCase)

    assert(Ukrainian.latinToCyrillic("Myroslava") == "Мирослава")
    assert(Ukrainian.latinToCyrillic("MYROSLAVA") == "Мирослава".toUpperCase)

    assert(Ukrainian.latinToCyrillic("sut'") == "суть")
    assert(Ukrainian.latinToCyrillic("SUT'") == "суть".toUpperCase)
  }

  test("Retain apostrophes") {
    // From https://uk.wikipedia.org/wiki/%D0%90%D0%BF%D0%BE%D1%81%D1%82%D1%80%D0%BE%D1%84
    assert(Ukrainian.latinToCyrillic("Znam'ianka") == "Знам'янка")
    assert(Ukrainian.latinToCyrillic("kur'ier") == "кур'єр")
    assert(Ukrainian.latinToCyrillic("b'iu") == "б'ю")
    assert(Ukrainian.latinToCyrillic("p'ie") == "п'є")
    assert(Ukrainian.latinToCyrillic("v'iazy") == "в'язи")
    assert(Ukrainian.latinToCyrillic("u zdorov'ji") == "у здоров'ї")
    assert(Ukrainian.latinToCyrillic("m'iaso") == "м'ясо")
    assert(Ukrainian.latinToCyrillic("rum'ianyi") == "рум'яний")
    assert(Ukrainian.latinToCyrillic("tim'ia") == "тім'я")
    assert(Ukrainian.latinToCyrillic("meref'ians'kyi") == "мереф'янський")
    assert(Ukrainian.latinToCyrillic("V'iacheslav") == "В'ячеслав")
    assert(Ukrainian.latinToCyrillic("Stef'iuk") == "Стеф'юк")
    assert(Ukrainian.latinToCyrillic("verb'ia") == "верб'я")
    assert(Ukrainian.latinToCyrillic("torf'ianyi") == "торф'яний")
    assert(Ukrainian.latinToCyrillic("cherv'iak") == "черв'як")
    assert(Ukrainian.latinToCyrillic("zv'iazok") == "зв'язок")
    assert(Ukrainian.latinToCyrillic("zv'ialyty") == "зв'ялити")
    assert(Ukrainian.latinToCyrillic("pidv'iazaty") == "підв'язати")
    assert(Ukrainian.latinToCyrillic("rozm'iakshyty") == "розм'якшити")
    assert(Ukrainian.latinToCyrillic("bur'ian") == "бур'ян")
    assert(Ukrainian.latinToCyrillic("mizhhir'ia") == "міжгір'я")
    assert(Ukrainian.latinToCyrillic("pir'ia") == "пір'я")
    assert(Ukrainian.latinToCyrillic("matir'iu") == "матір'ю")
    assert(Ukrainian.latinToCyrillic("na podvir'ji") == "на подвір'ї")
    assert(Ukrainian.latinToCyrillic("bez'iazykyi") == "без'язикий")
    assert(Ukrainian.latinToCyrillic("vid'jizd") == "від'їзд")
    assert(Ukrainian.latinToCyrillic("z'iednanyi") == "з'єднаний")
    assert(Ukrainian.latinToCyrillic("z'jikhaty") == "з'їхати")
    assert(Ukrainian.latinToCyrillic("z'iavytysia") == "з'явитися")
    assert(Ukrainian.latinToCyrillic("ob'iem") == "об'єм")
    assert(Ukrainian.latinToCyrillic("pid'jikhaty") == "під'їхати")
    assert(Ukrainian.latinToCyrillic("roz'iushyty") == "роз'юшити")
    assert(Ukrainian.latinToCyrillic("roz'iasnyty") == "роз'яснити")
    assert(Ukrainian.latinToCyrillic("dyt'iasla") == "дит'ясла")
    assert(Ukrainian.latinToCyrillic("pan'ievropeis'kyi") == "пан'європейський")
    assert(Ukrainian.latinToCyrillic("piv'iabluka") == "пів'яблука")
    assert(Ukrainian.latinToCyrillic("trans'ievropeis'kyi") == "транс'європейський")
    assert(Ukrainian.latinToCyrillic("im'ia") == "ім'я")
    assert(Ukrainian.latinToCyrillic("M'iakyi") == "М'який")
    assert(Ukrainian.latinToCyrillic("ob'iekt") == "об'єкт")
    assert(Ukrainian.latinToCyrillic("pam'iataty") == "пам'ятати")
  }

  test("Restore soft signs") {
    assert(Ukrainian.latinToCyrillic("Ukrajins'kyi") == "Український")
    assert(Ukrainian.latinToCyrillic("Yul'ien") == "Юльєн")
    assert(Ukrainian.latinToCyrillic("Sedertel'ie") == "Седертельє")
    assert(Ukrainian.latinToCyrillic("Lil'iedal'") == "Лільєдаль")
    assert(Ukrainian.latinToCyrillic("Ven'ian") == "Веньян")
    assert(Ukrainian.latinToCyrillic("N'iurd") == "Ньюрд")
    assert(Ukrainian.latinToCyrillic("piran'ia") == "піранья")
    assert(Ukrainian.latinToCyrillic("piran'ji") == "піраньї")
    assert(Ukrainian.latinToCyrillic("port'ie") == "портьє")
    assert(Ukrainian.latinToCyrillic("port'iera") == "портьєра")
    assert(Ukrainian.latinToCyrillic("rel'ief") == "рельєф")
    assert(Ukrainian.latinToCyrillic("N'iuton") == "Ньютон")
    assert(Ukrainian.latinToCyrillic("batal'ion") == "батальйон")
    assert(Ukrainian.latinToCyrillic("kan'ion") == "каньйон")
    assert(Ukrainian.latinToCyrillic("montan'iar") == "монтаньяр")
    assert(Ukrainian.latinToCyrillic("malen'kyi") == "маленький")
    assert(Ukrainian.latinToCyrillic("vydaiet'sia", apostrophes = false) ==
      removeApostropheAndSoftSign("видається"))
    assert(Ukrainian.latinToCyrillic("vydaiet'sia") == "видається")

    // Uncommon words where the soft sign cannot be restored
    // assert(Ukrainian.latinToCyrillic("Yil't'iaur") == "Їльтьяур")
    // assert(Ukrainian.latinToCyrillic("Gran'ierde") == "Ґраньєрде")

    // From https://uk.wikipedia.org/wiki/%D0%AC
    assert(Ukrainian.latinToCyrillic("vis'") == "вісь")
    assert(Ukrainian.latinToCyrillic("gedz'") == "ґедзь")
    assert(Ukrainian.latinToCyrillic("kin'") == "кінь")
    assert(Ukrainian.latinToCyrillic("mid'") == "мідь")
    assert(Ukrainian.latinToCyrillic("namoroz'") == "наморозь")
    assert(Ukrainian.latinToCyrillic("palets'") == "палець")
    assert(Ukrainian.latinToCyrillic("sut'") == "суть")
    assert(Ukrainian.latinToCyrillic("shvets'") == "швець")
    assert(Ukrainian.latinToCyrillic("blyz'ko") == "близько")
    assert(Ukrainian.latinToCyrillic("vos'myi") == "восьмий")
    assert(Ukrainian.latinToCyrillic("han'ba") == "ганьба")
    assert(Ukrainian.latinToCyrillic("Hryts'ko") == "Грицько")
    assert(Ukrainian.latinToCyrillic("diad'ko") == "дядько")
    assert(Ukrainian.latinToCyrillic("kil'tse") == "кільце")
    assert(Ukrainian.latinToCyrillic("molot'ba") == "молотьба")
    assert(Ukrainian.latinToCyrillic("d'ohot'") == "дьоготь")
    assert(Ukrainian.latinToCyrillic("dz'ob") == "дзьоб")
    assert(Ukrainian.latinToCyrillic("l'on") == "льон")
    assert(Ukrainian.latinToCyrillic("s'omyi") == "сьомий")
    assert(Ukrainian.latinToCyrillic("tr'okh") == "трьох")
    assert(Ukrainian.latinToCyrillic("t'okhkaty") == "тьохкати")
  }

  test("Disambiguate soft signs and apostrophes") {
    assert(Ukrainian.latinToCyrillic("p'iat'") == "п'ять")
  }

  test("Examples") {
    assert(Ukrainian.latinToCyrillic("virte") == "вірте")
    assert(Ukrainian.latinToCyrillic("kobzar") == "кобзар")
    assert(Ukrainian.latinToCyrillic("likar") == "лікар")
    assert(Ukrainian.latinToCyrillic("perevir") == "перевір")
    assert(Ukrainian.latinToCyrillic("sekretar") == "секретар")
    assert(Ukrainian.latinToCyrillic("teper") == "тепер")
    assert(Ukrainian.latinToCyrillic("shkoliar") == "школяр")
    assert(Ukrainian.latinToCyrillic("Kharkiv") == "Харків")
    assert(Ukrainian.latinToCyrillic("Al'bert Einshtein") == "Альберт Ейнштейн")
    assert(Ukrainian.latinToCyrillic("zdaiut'sia") == "здаються")
    assert(Ukrainian.latinToCyrillic("postiynomu") == "постійному")
    assert(Ukrainian.latinToCyrillic("Yota") == "Йота")
  }

  test("s vs c") {
    assert(Ukrainian.latinToCyrillic("vlacnym") == "вланим")
    assert(Ukrainian.latinToCyrillic("vlasnym") == "власним")
  }
}
