package translit

import org.scalatest.FunSuite

class UkrainianSpec extends FunSuite {
  val correctMapping = List(
    "Алушта" -> "Alushta",
    "Андрій" -> "Andrij",
    "Борщагівка" -> "Borshchagivka",
    "Борисенко" -> "Borysenko",
    "Вінниця" -> "Vinnytsya",
    "Володимир" -> "Volodymyr",
    "Гадяч" -> "Gadyach",
    "Богдан" -> "Bogdan",
    "Згурський" -> "Zgurskyj",
    "Ґалаґан" -> "G'alag'an",
    "Ґорґани" -> "G'org'any",
    "Донецьк" -> "Donetsk",
    "Дмитро" -> "Dmytro",
    "Рівне" -> "Rivne",
    "Олег" -> "Oleg",
    "Есмань" -> "Esman",
    "Єнакієве" -> "Yenakiyeve",
    "Гаєвич" -> "Gayevych",
    "Короп'є" -> "Koropye",
    "Житомир" -> "Zhytomyr",
    "Жанна" -> "Zhanna",
    "Жежелів" -> "Zhezheliv",
    "Закарпаття" -> "Zakarpattya",
    "Казимирчук" -> "Kazymyrchuk",
    "Медвин" -> "Medvyn",
    "Михайленко" -> "Myhajlenko",
    "Іванків" -> "Ivankiv",
    "Іващенко" -> "Ivashchenko",
    "Їжакевич" -> "Yizhakevych",
    "Кадиївка" -> "Kadyyivka",
    "Йосипівка" -> "Josypivka",
    "Стрий" -> "Stryj",
    "Олексій" -> "Oleksij",
    "Київ" -> "Kyyiv",
    "Коваленко" -> "Kovalenko",
    "Лебедин" -> "Lebedyn",
    "Леонід" -> "Leonid",
    "Миколаїв" -> "Mykolayiv",
    "Маринич" -> "Marynych",
    "Ніжин" -> "Nizhyn",
    "Наталія" -> "Nataliya",
    "Одеса" -> "Odesa",
    "Онищенко" -> "Onyshchenko",
    "Полтава" -> "Poltava",
    "Петро" -> "Petro",
    "Решетилівка" -> "Reshetylivka",
    "Рибчинський" -> "Rybchynskyj",
    "Суми" -> "Sumy",
    "Соломія" -> "Solomiya",
    "Тернопіль" -> "Ternopil",
    "Троць" -> "Trots",
    "Ужгород" -> "Uzhgorod",
    "Уляна" -> "Ulyana",
    "Фастів" -> "Fastiv",
    "Філіпчук" -> "Filipchuk",
    "Харків" -> "Harkiv",
    "Христина" -> "Hrystyna",
    "Біла Церква" -> "Bila Tserkva",
    "Стеценко" -> "Stetsenko",
    "Чернівці" -> "Chernivtsi",
    "Шевченко" -> "Shevchenko",
    "Шостка" -> "Shostka",
    "Кишеньки" -> "Kyshenky",
    "Щербухи" -> "Shcherbuhy",
    "Гоща" -> "Goshcha",
    "Гаращенко" -> "Garashchenko",
    "Юрій" -> "Yurij",
    "Корюківка" -> "Koryukivka",
    "Яготин" -> "Yagotyn",
    "Ярошенко" -> "Yaroshenko",
    "Костянтин" -> "Kostyantyn",
    "Знам'янка" -> "Znamyanka",
    "Феодосія" -> "Feodosiya"
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

  test("yi / yy") {
    assert(Ukrainian.latinToCyrillic("Kyyiv") == "Київ")
    assert(Ukrainian.latinToCyrillic("Kryyivka") == "Криївка")
    assert(Ukrainian.latinToCyrillic("Kategoriyi") == "Категорії")

    assert(Ukrainian.latinToCyrillic("Stryjs'kyj park") == "Стрийський парк")
    assert(Ukrainian.latinToCyrillic("Stryjs'ka") == "Стрийська")
    assert(Ukrainian.latinToCyrillic("kofeyin") == "кофеїн")
    assert(Ukrainian.latinToCyrillic("pryjnyatyh") == "прийнятих")
    assert(Ukrainian.latinToCyrillic("Staryj") == "Старий")
    assert(Ukrainian.latinToCyrillic("Avtomyjka") == "Автомийка")
  }

  test("Case sensitivity") {
    assert(Ukrainian.latinToCyrillic("Mar'yana") == "Мар'яна")
    assert(Ukrainian.latinToCyrillic("MAR'YANA") == "Мар'яна".toUpperCase)

    assert(Ukrainian.latinToCyrillic("Yaroslava") == "Ярослава")
    assert(Ukrainian.latinToCyrillic("YAROSLAVA") == "Ярослава".toUpperCase)

    assert(Ukrainian.latinToCyrillic("Myroslava") == "Мирослава")
    assert(Ukrainian.latinToCyrillic("MYROSLAVA") == "Мирослава".toUpperCase)

    assert(Ukrainian.latinToCyrillic("Al'bert".toUpperCase) == "Альберт".toUpperCase)
    assert(Ukrainian.latinToCyrillic("Zgurs'kyj".toUpperCase) == "Згурський".toUpperCase)

    assert(Ukrainian.latinToCyrillic("sut'") == "суть")
    assert(Ukrainian.latinToCyrillic("SUT'") == "суть".toUpperCase)
  }

  test("Retain apostrophes") {
    // From https://uk.wikipedia.org/wiki/%D0%90%D0%BF%D0%BE%D1%81%D1%82%D1%80%D0%BE%D1%84
    assert(Ukrainian.latinToCyrillic("Znam'yanka") == "Знам'янка")
    assert(Ukrainian.latinToCyrillic("sim'yu") == "сім'ю")
    assert(Ukrainian.latinToCyrillic("kur'yer") == "кур'єр")
    assert(Ukrainian.latinToCyrillic("b'yu") == "б'ю")
    assert(Ukrainian.latinToCyrillic("p'ye") == "п'є")
    assert(Ukrainian.latinToCyrillic("v'yazy") == "в'язи")
    assert(Ukrainian.latinToCyrillic("u zdorov'yi") == "у здоров'ї")
    assert(Ukrainian.latinToCyrillic("m'yaso") == "м'ясо")
    assert(Ukrainian.latinToCyrillic("rum'yanyj") == "рум'яний")
    assert(Ukrainian.latinToCyrillic("tim'ya") == "тім'я")
    assert(Ukrainian.latinToCyrillic("meref'yans'kyj") == "мереф'янський")
    assert(Ukrainian.latinToCyrillic("V'yacheslav") == "В'ячеслав")
    assert(Ukrainian.latinToCyrillic("Stef'yuk") == "Стеф'юк")
    assert(Ukrainian.latinToCyrillic("verb'ya") == "верб'я")
    assert(Ukrainian.latinToCyrillic("torf'yanyj") == "торф'яний")
    assert(Ukrainian.latinToCyrillic("cherv'yak") == "черв'як")
    assert(Ukrainian.latinToCyrillic("zv'yazok") == "зв'язок")
    assert(Ukrainian.latinToCyrillic("zv'yalyty") == "зв'ялити")
    assert(Ukrainian.latinToCyrillic("pidv'yazaty") == "підв'язати")
    assert(Ukrainian.latinToCyrillic("rozm'yakshyty") == "розм'якшити")
    assert(Ukrainian.latinToCyrillic("bur'yan") == "бур'ян")
    assert(Ukrainian.latinToCyrillic("mizhgir'ya") == "міжгір'я")
    assert(Ukrainian.latinToCyrillic("pir'ya") == "пір'я")
    assert(Ukrainian.latinToCyrillic("matir'yu") == "матір'ю")
    assert(Ukrainian.latinToCyrillic("na podvir'yi") == "на подвір'ї")
    assert(Ukrainian.latinToCyrillic("bez'yazykyj") == "без'язикий")
    assert(Ukrainian.latinToCyrillic("vid'yizd") == "від'їзд")
    assert(Ukrainian.latinToCyrillic("z'yednanyj") == "з'єднаний")
    assert(Ukrainian.latinToCyrillic("z'yihaty") == "з'їхати")
    assert(Ukrainian.latinToCyrillic("z'yavytysya") == "з'явитися")
    assert(Ukrainian.latinToCyrillic("ob'yem") == "об'єм")
    assert(Ukrainian.latinToCyrillic("pid'yihaty") == "під'їхати")
    assert(Ukrainian.latinToCyrillic("roz'yushyty") == "роз'юшити")
    assert(Ukrainian.latinToCyrillic("roz'yasnyty") == "роз'яснити")
    assert(Ukrainian.latinToCyrillic("dyt'yasla") == "дит'ясла")
    assert(Ukrainian.latinToCyrillic("pan'yevropejs'kyj") == "пан'європейський")
    assert(Ukrainian.latinToCyrillic("piv'yabluka") == "пів'яблука")
    assert(Ukrainian.latinToCyrillic("trans'yevropejs'kyj") == "транс'європейський")
    assert(Ukrainian.latinToCyrillic("im'ya") == "ім'я")
    assert(Ukrainian.latinToCyrillic("M'yakyj") == "М'який")
    assert(Ukrainian.latinToCyrillic("ob'yekt") == "об'єкт")
    assert(Ukrainian.latinToCyrillic("pam'yataty") == "пам'ятати")
    assert(Ukrainian.latinToCyrillic("ad'yutant") == "ад'ютант")
    assert(Ukrainian.latinToCyrillic("in'yektsiya") == "ін'єкція")
    assert(Ukrainian.latinToCyrillic("kon'yunktyvit") == "кон'юнктивіт")
    assert(Ukrainian.latinToCyrillic("kon'yunktura") == "кон'юнктура")
    assert(Ukrainian.latinToCyrillic("Min'yust") == "Мін'юст")
  }

  test("Restore soft signs") {
    assert(Ukrainian.latinToCyrillic("Ukrayins'kyj") == "Український")
    assert(Ukrainian.latinToCyrillic("Yul'yen") == "Юльєн")
    assert(Ukrainian.latinToCyrillic("Sedertel'ye") == "Седертельє")
    assert(Ukrainian.latinToCyrillic("Lil'yedal'") == "Лільєдаль")
    assert(Ukrainian.latinToCyrillic("Ven'yan") == "Веньян")
    assert(Ukrainian.latinToCyrillic("piran'ya") == "піранья")
    assert(Ukrainian.latinToCyrillic("piran'yi") == "піраньї")
    assert(Ukrainian.latinToCyrillic("port'ye") == "портьє")
    assert(Ukrainian.latinToCyrillic("port'yera") == "портьєра")
    assert(Ukrainian.latinToCyrillic("rel'yef") == "рельєф")
    assert(Ukrainian.latinToCyrillic("batal'jon") == "батальйон")
    assert(Ukrainian.latinToCyrillic("kan'jon") == "каньйон")
    assert(Ukrainian.latinToCyrillic("montan'yar") == "монтаньяр")
    assert(Ukrainian.latinToCyrillic("malen'kyj") == "маленький")
    assert(Ukrainian.latinToCyrillic("vydayet'sya") == "видається")
    assert(Ukrainian.latinToCyrillic("vydayet'sya") == "видається")
    assert(Ukrainian.latinToCyrillic("pas'yans") == "пасьянс")
    assert(Ukrainian.latinToCyrillic("kon'yak") == "коньяк")

    // From https://uk.wikipedia.org/wiki/%D0%AC
    assert(Ukrainian.latinToCyrillic("vis'") == "вісь")
    assert(Ukrainian.latinToCyrillic("g'edz'") == "ґедзь")
    assert(Ukrainian.latinToCyrillic("kin'") == "кінь")
    assert(Ukrainian.latinToCyrillic("mid'") == "мідь")
    assert(Ukrainian.latinToCyrillic("namoroz'") == "наморозь")
    assert(Ukrainian.latinToCyrillic("palets'") == "палець")
    assert(Ukrainian.latinToCyrillic("sut'") == "суть")
    assert(Ukrainian.latinToCyrillic("shvets'") == "швець")
    assert(Ukrainian.latinToCyrillic("blyz'ko") == "близько")
    assert(Ukrainian.latinToCyrillic("vos'myj") == "восьмий")
    assert(Ukrainian.latinToCyrillic("gan'ba") == "ганьба")
    assert(Ukrainian.latinToCyrillic("Gryts'ko") == "Грицько")
    assert(Ukrainian.latinToCyrillic("dyad'ko") == "дядько")
    assert(Ukrainian.latinToCyrillic("kil'tse") == "кільце")
    assert(Ukrainian.latinToCyrillic("molot'ba") == "молотьба")
    assert(Ukrainian.latinToCyrillic("d'ogot'") == "дьоготь")
    assert(Ukrainian.latinToCyrillic("dz'ob") == "дзьоб")
    assert(Ukrainian.latinToCyrillic("l'on") == "льон")
    assert(Ukrainian.latinToCyrillic("s'omyj") == "сьомий")
    assert(Ukrainian.latinToCyrillic("tr'oh") == "трьох")
    assert(Ukrainian.latinToCyrillic("t'ohkaty") == "тьохкати")

    // TODO Words where the soft sign cannot be restored
    // assert(Ukrainian.latinToCyrillic("N'yurd") == "Ньюрд")
    // assert(Ukrainian.latinToCyrillic("N'yuton") == "Ньютон")
    // assert(Ukrainian.latinToCyrillic("Yil't'yaur") == "Їльтьяур")
    // assert(Ukrainian.latinToCyrillic("Gran'yerde") == "Ґраньєрде")
    // assert(Ukrainian.latinToCyrillic("brakon'yer") == "браконьєр")
    // assert(Ukrainian.latinToCyrillic("buton'yerka") == "бутоньєрка")
    // assert(Ukrainian.latinToCyrillic("vin'yetka") == "віньєтка")
    // assert(Ukrainian.latinToCyrillic("dos'ye") == "досьє")
  }

  test("Disambiguate soft signs and apostrophes") {
    assert(Ukrainian.latinToCyrillic("p'yat'") == "п'ять")
  }

  test("Examples") {
    assert(Ukrainian.latinToCyrillic("virte") == "вірте")
    assert(Ukrainian.latinToCyrillic("kobzar") == "кобзар")
    assert(Ukrainian.latinToCyrillic("likar") == "лікар")
    assert(Ukrainian.latinToCyrillic("perevir") == "перевір")
    assert(Ukrainian.latinToCyrillic("sekretar") == "секретар")
    assert(Ukrainian.latinToCyrillic("teper") == "тепер")
    assert(Ukrainian.latinToCyrillic("shkolyar") == "школяр")
    assert(Ukrainian.latinToCyrillic("Harkiv") == "Харків")
    assert(Ukrainian.latinToCyrillic("Al'bert Ejnshtejn") == "Альберт Ейнштейн")
    assert(Ukrainian.latinToCyrillic("zdayut'sya") == "здаються")
    assert(Ukrainian.latinToCyrillic("postijnomu") == "постійному")
    assert(Ukrainian.latinToCyrillic("Jota") == "Йота")
    assert(Ukrainian.latinToCyrillic("Puzata Hata") == "Пузата Хата")
  }

  test("s vs c") {
    assert(Ukrainian.latinToCyrillic("vlacnym") == "влацним")
    assert(Ukrainian.latinToCyrillic("vlasnym") == "власним")
  }

  test("сх") {
    assert(Ukrainian.latinToCyrillic("s|hyl'nist'") == "схильність")
    assert(Ukrainian.latinToCyrillic("s|hopyv") == "схопив")
  }

  test("Offsets") {
    assert(Ukrainian.latinToCyrillicOfs("shch", 0) == (0, 'с'))
    assert(Ukrainian.latinToCyrillicOfs("shch", 1) == (-1, 'ш'))
    assert(Ukrainian.latinToCyrillicOfs("shch", 2) == (0, 'ц'))
    assert(Ukrainian.latinToCyrillicOfs("shch", 3) == (-2, 'щ'))
    assert(Ukrainian.latinToCyrillicOfs("yeshch", 5) == (-2, 'щ'))

    assert(Ukrainian.latinToCyrillicOfs("pryvit", 0) == (0, 'п'))

    assert(Ukrainian.latinToCyrillicOfs("ya", 0) == (0, 'и'))
    assert(Ukrainian.latinToCyrillicOfs("ya", 1) == (-1, 'я'))
    assert(Ukrainian.latinToCyrillicOfs("ye", 0) == (0, 'и'))
    assert(Ukrainian.latinToCyrillicOfs("ye", 1) == (-1, 'є'))

    assert(Ukrainian.latinToCyrillicOfs("ay", 0) == (0, 'а'))
    assert(Ukrainian.latinToCyrillicOfs("aya", 2) == (-1, 'я'))

    assert(Ukrainian.latinToCyrillicOfs("yach", 3) == (-1, 'ч'))
    assert(Ukrainian.latinToCyrillicOfs("vych", 1) == (0, 'и'))
    assert(Ukrainian.latinToCyrillicOfs("vych", 3) == (-1, 'ч'))

    assert(Ukrainian.latinToCyrillicOfs("zg", 0) == (0, 'з'))
    assert(Ukrainian.latinToCyrillicOfs("zg", 1) == (0, 'г'))
  }

  test("Convenience mappings") {
    assert(Ukrainian.latinToCyrillic("cqwx") == "цщшж")
  }

  test("Incremental transliteration") {
    assert(Ukrainian.latinToCyrillic("zg") == "зг")
    assert(Ukrainian.latinToCyrillic("зg", incremental = true) == "зг")

    assert(Ukrainian.latinToCyrillic("Шцh", incremental = true) == "Щ")
  }
}
