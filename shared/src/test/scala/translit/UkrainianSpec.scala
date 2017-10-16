package translit

import org.scalatest.FunSuite

class UkrainianSpec extends FunSuite {
  val correctMapping = List(
    "Алушта" -> "Alushta",
    "Андрій" -> "Andriy",
    "Борщагівка" -> "Borshchahivka",
    "Борисенко" -> "Borysenko",
    "Вінниця" -> "Vinnytsya",
    "Володимир" -> "Volodymyr",
    "Гадяч" -> "Hadyach",
    "Богдан" -> "Bohdan",
    "Згурський" -> "Zghurskyy",
    "Ґалаґан" -> "Galagan",
    "Ґорґани" -> "Gorgany",
    "Донецьк" -> "Donetsk",
    "Дмитро" -> "Dmytro",
    "Рівне" -> "Rivne",
    "Олег" -> "Oleh",
    "Есмань" -> "Esman",
    "Єнакієве" -> "Yenakiyeve",
    "Гаєвич" -> "Hayevych",
    "Короп'є" -> "Koropye",
    "Житомир" -> "Zhytomyr",
    "Жанна" -> "Zhanna",
    "Жежелів" -> "Zhezheliv",
    "Закарпаття" -> "Zakarpattya",
    "Казимирчук" -> "Kazymyrchuk",
    "Медвин" -> "Medvyn",
    "Михайленко" -> "Mykhaylenko",
    "Іванків" -> "Ivankiv",
    "Іващенко" -> "Ivashchenko",
    "Їжакевич" -> "Yizhakevych",
    "Кадиївка" -> "Kadyyivka",
    "Йосипівка" -> "Yosypivka",
    "Стрий" -> "Stryy",
    "Олексій" -> "Oleksiy",
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
    "Рибчинський" -> "Rybchynskyy",
    "Суми" -> "Sumy",
    "Соломія" -> "Solomiya",
    "Тернопіль" -> "Ternopil",
    "Троць" -> "Trots",
    "Ужгород" -> "Uzhhorod",
    "Уляна" -> "Ulyana",
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
    "Корюківка" -> "Koryukivka",
    "Яготин" -> "Yahotyn",
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

  test("ii / yy") {
    assert(Ukrainian.latinToCyrillic("Kyyiv") == "Київ")
    assert(Ukrainian.latinToCyrillic("Kryyivka") == "Криївка")
    assert(Ukrainian.latinToCyrillic("Katehoriyi") == "Категорії")

    assert(Ukrainian.latinToCyrillic("Stryys'kyy park") == "Стрийський парк")
    assert(Ukrainian.latinToCyrillic("Stryys'ka") == "Стрийська")
    assert(Ukrainian.latinToCyrillic("kofeyin") == "кофеїн")
    assert(Ukrainian.latinToCyrillic("pryynyatykh") == "прийнятих")
    assert(Ukrainian.latinToCyrillic("Staryy") == "Старий")
    assert(Ukrainian.latinToCyrillic("Avtomyyka") == "Автомийка")
  }

  test("Case sensitivity") {
    assert(Ukrainian.latinToCyrillic("Mar'yana") == "Мар'яна")
    assert(Ukrainian.latinToCyrillic("MAR'YANA") == "Мар'яна".toUpperCase)

    assert(Ukrainian.latinToCyrillic("Yaroslava") == "Ярослава")
    assert(Ukrainian.latinToCyrillic("YAROSLAVA") == "Ярослава".toUpperCase)

    assert(Ukrainian.latinToCyrillic("Myroslava") == "Мирослава")
    assert(Ukrainian.latinToCyrillic("MYROSLAVA") == "Мирослава".toUpperCase)

    assert(Ukrainian.latinToCyrillic("sut'") == "суть")
    assert(Ukrainian.latinToCyrillic("SUT'") == "суть".toUpperCase)
  }

  test("Retain apostrophes") {
    // From https://uk.wikipedia.org/wiki/%D0%90%D0%BF%D0%BE%D1%81%D1%82%D1%80%D0%BE%D1%84
    assert(Ukrainian.latinToCyrillic("Znam'yanka") == "Знам'янка")
    assert(Ukrainian.latinToCyrillic("kur'yer") == "кур'єр")
    assert(Ukrainian.latinToCyrillic("b'yu") == "б'ю")
    assert(Ukrainian.latinToCyrillic("p'ye") == "п'є")
    assert(Ukrainian.latinToCyrillic("v'yazy") == "в'язи")
    assert(Ukrainian.latinToCyrillic("u zdorov'yi") == "у здоров'ї")
    assert(Ukrainian.latinToCyrillic("m'yaso") == "м'ясо")
    assert(Ukrainian.latinToCyrillic("rum'yanyy") == "рум'яний")
    assert(Ukrainian.latinToCyrillic("tim'ya") == "тім'я")
    assert(Ukrainian.latinToCyrillic("meref'yans'kyy") == "мереф'янський")
    assert(Ukrainian.latinToCyrillic("V'yacheslav") == "В'ячеслав")
    assert(Ukrainian.latinToCyrillic("Stef'yuk") == "Стеф'юк")
    assert(Ukrainian.latinToCyrillic("verb'ya") == "верб'я")
    assert(Ukrainian.latinToCyrillic("torf'yanyy") == "торф'яний")
    assert(Ukrainian.latinToCyrillic("cherv'yak") == "черв'як")
    assert(Ukrainian.latinToCyrillic("zv'yazok") == "зв'язок")
    assert(Ukrainian.latinToCyrillic("zv'yalyty") == "зв'ялити")
    assert(Ukrainian.latinToCyrillic("pidv'yazaty") == "підв'язати")
    assert(Ukrainian.latinToCyrillic("rozm'yakshyty") == "розм'якшити")
    assert(Ukrainian.latinToCyrillic("bur'yan") == "бур'ян")
    assert(Ukrainian.latinToCyrillic("mizhhir'ya") == "міжгір'я")
    assert(Ukrainian.latinToCyrillic("pir'ya") == "пір'я")
    assert(Ukrainian.latinToCyrillic("matir'yu") == "матір'ю")
    assert(Ukrainian.latinToCyrillic("na podvir'yi") == "на подвір'ї")
    assert(Ukrainian.latinToCyrillic("bez'yazykyy") == "без'язикий")
    assert(Ukrainian.latinToCyrillic("vid'yizd") == "від'їзд")
    assert(Ukrainian.latinToCyrillic("z'yednanyy") == "з'єднаний")
    assert(Ukrainian.latinToCyrillic("z'yikhaty") == "з'їхати")
    assert(Ukrainian.latinToCyrillic("z'yavytysya") == "з'явитися")
    assert(Ukrainian.latinToCyrillic("ob'yem") == "об'єм")
    assert(Ukrainian.latinToCyrillic("pid'yikhaty") == "під'їхати")
    assert(Ukrainian.latinToCyrillic("roz'yushyty") == "роз'юшити")
    assert(Ukrainian.latinToCyrillic("roz'yasnyty") == "роз'яснити")
    assert(Ukrainian.latinToCyrillic("dyt'yasla") == "дит'ясла")
    assert(Ukrainian.latinToCyrillic("pan'yevropeys'kyy") == "пан'європейський")
    assert(Ukrainian.latinToCyrillic("piv'yabluka") == "пів'яблука")
    assert(Ukrainian.latinToCyrillic("trans'yevropeys'kyy") == "транс'європейський")
    assert(Ukrainian.latinToCyrillic("im'ya") == "ім'я")
    assert(Ukrainian.latinToCyrillic("M'yakyy") == "М'який")
    assert(Ukrainian.latinToCyrillic("ob'yekt") == "об'єкт")
    assert(Ukrainian.latinToCyrillic("pam'yataty") == "пам'ятати")
    assert(Ukrainian.latinToCyrillic("ad'yutant") == "ад'ютант")
    assert(Ukrainian.latinToCyrillic("in'yektsiya") == "ін'єкція")
    assert(Ukrainian.latinToCyrillic("kon'yunktyvit") == "кон'юнктивіт")
    assert(Ukrainian.latinToCyrillic("kon'yunktura") == "кон'юнктура")
    assert(Ukrainian.latinToCyrillic("Min'yust") == "Мін'юст")
  }

  test("Restore soft signs") {
    assert(Ukrainian.latinToCyrillic("Ukrayins'kyy") == "Український")
    assert(Ukrainian.latinToCyrillic("Yul'yen") == "Юльєн")
    assert(Ukrainian.latinToCyrillic("Sedertel'ye") == "Седертельє")
    assert(Ukrainian.latinToCyrillic("Lil'yedal'") == "Лільєдаль")
    assert(Ukrainian.latinToCyrillic("Ven'yan") == "Веньян")
    assert(Ukrainian.latinToCyrillic("piran'ya") == "піранья")
    assert(Ukrainian.latinToCyrillic("piran'yi") == "піраньї")
    assert(Ukrainian.latinToCyrillic("port'ye") == "портьє")
    assert(Ukrainian.latinToCyrillic("port'yera") == "портьєра")
    assert(Ukrainian.latinToCyrillic("rel'yef") == "рельєф")
    assert(Ukrainian.latinToCyrillic("batal'yon") == "батальйон")
    assert(Ukrainian.latinToCyrillic("kan'yon") == "каньйон")
    assert(Ukrainian.latinToCyrillic("montan'yar") == "монтаньяр")
    assert(Ukrainian.latinToCyrillic("malen'kyy") == "маленький")
    assert(Ukrainian.latinToCyrillic("vydayet'sya", apostrophes = false) ==
      removeApostropheAndSoftSign("видається"))
    assert(Ukrainian.latinToCyrillic("vydayet'sya") == "видається")
    assert(Ukrainian.latinToCyrillic("pas'yans") == "пасьянс")
    assert(Ukrainian.latinToCyrillic("kon'yak") == "коньяк")

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
    assert(Ukrainian.latinToCyrillic("vos'myy") == "восьмий")
    assert(Ukrainian.latinToCyrillic("han'ba") == "ганьба")
    assert(Ukrainian.latinToCyrillic("Hryts'ko") == "Грицько")
    assert(Ukrainian.latinToCyrillic("dyad'ko") == "дядько")
    assert(Ukrainian.latinToCyrillic("kil'tse") == "кільце")
    assert(Ukrainian.latinToCyrillic("molot'ba") == "молотьба")
    assert(Ukrainian.latinToCyrillic("d'ohot'") == "дьоготь")
    assert(Ukrainian.latinToCyrillic("dz'ob") == "дзьоб")
    assert(Ukrainian.latinToCyrillic("l'on") == "льон")
    assert(Ukrainian.latinToCyrillic("s'omyy") == "сьомий")
    assert(Ukrainian.latinToCyrillic("tr'okh") == "трьох")
    assert(Ukrainian.latinToCyrillic("t'okhkaty") == "тьохкати")

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
    assert(Ukrainian.latinToCyrillic("Kharkiv") == "Харків")
    assert(Ukrainian.latinToCyrillic("Al'bert Eynshteyn") == "Альберт Ейнштейн")
    assert(Ukrainian.latinToCyrillic("zdayut'sya") == "здаються")
    assert(Ukrainian.latinToCyrillic("postiynomu") == "постійному")
    assert(Ukrainian.latinToCyrillic("Yota") == "Йота")
  }

  test("s vs c") {
    assert(Ukrainian.latinToCyrillic("vlacnym") == "вланим")
    assert(Ukrainian.latinToCyrillic("vlasnym") == "власним")
  }
}
