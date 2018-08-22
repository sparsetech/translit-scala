package translit

import Helpers._

object Ukrainian extends Language {
  val uniGrams = Map(
    'a' -> 'а',
    'b' -> 'б',
    'd' -> 'д',
    'e' -> 'е',
    'f' -> 'ф',
    'g' -> 'г',
    'h' -> 'х',
    'i' -> 'і',
    'j' -> 'й',
    'k' -> 'к',
    'l' -> 'л',
    'm' -> 'м',
    'n' -> 'н',
    'o' -> 'о',
    'p' -> 'п',
    'r' -> 'р',
    's' -> 'с',
    't' -> 'т',
    'u' -> 'у',
    'v' -> 'в',
    'y' -> 'и',
    'z' -> 'з',

    // Mappings for more convenient typing. Allows us to cover every letter of
    // the Latin alphabet
    'c' -> 'ц',
    'q' -> 'щ',
    'w' -> 'ш',
    'x' -> 'ж'
  )

  val biGrams = Map(
    "ya" -> 'я',
    "ye" -> 'є',
    "yi" -> 'ї',
    "yu" -> 'ю',

    "g'" -> 'ґ',

    "ch" -> 'ч',
    "sh" -> 'ш',
    "ts" -> 'ц',
    "zh" -> 'ж',

    // With the vertical bar, transliteration can be disabled.
    "s|" -> 'с'
  )

  val triGrams = Map[String, Char]()

  val fourGrams = Map(
    "shch" -> 'щ'
  )

  val apostrophePatterns = Set(
    ('b', "ya"),
    ('b', "ye"),
    ('b', "yu"),
    ('d', "yu"),
    ('d', "yi"),
    ('f', "ya"),
    ('f', "yu"),
    ('m', "ya"),
    ('m', "yu"),
    ('n', "ye"),
    ('n', "yu"),
    ('p', "ya"),
    ('p', "ye"),
    ('r', "ya"),
    ('r', "ye"),
    ('r', "yu"),
    ('r', "yi"),
    ('s', "ye"),
    ('t', "ya"),
    ('v', "ya"),
    ('v', "yi"),
    ('z', "ya"),
    ('z', "ye"),
    ('z', "yu"),
    ('z', "yi")
  )

  def latinToCyrillicOne(left: String,
                         c: Char,
                         right: String,
                         apostrophes: Boolean = true): (Int, Char) = {
    val text = left + c
    val ofs = text.length
    if (ofs >= 4 &&
      fourGrams.contains(text.substring(ofs - 4, ofs).toLowerCase)
    ) {
      val chars    = text.substring(ofs - 4, ofs)
      val cyrillic = fourGrams(chars.toLowerCase)
      (-2, restoreCaseFirst(chars, cyrillic))
    } else if (ofs >= 3 &&
      triGrams.contains(text.substring(ofs - 3, ofs).toLowerCase)
    ) {
      val chars    = text.substring(ofs - 3, ofs)
      val cyrillic = triGrams(chars.toLowerCase)
      (-1, restoreCaseAll(chars, cyrillic))
    } else if (ofs >= 2 &&
      biGrams.contains(text.substring(ofs - 2, ofs).toLowerCase)
    ) {
      val chars = text.substring(ofs - 2, ofs)
      val cyrillic = biGrams(chars.toLowerCase)
      (-1, restoreCaseFirst(chars, cyrillic))
    } else if (uniGrams.contains(text(ofs - 1).toLower)) {
      val cyrillic = uniGrams(text(ofs - 1).toLower)
      (0, if (text(ofs - 1).isUpper) cyrillic.toUpper else cyrillic)
    } else if (ofs >= 2 && text(ofs - 1) == '\'' && apostrophes) {
      val last     = if (ofs >= 1) text(ofs - 2).toLower else '\u0000'
      val nextTwo  = right.take(2).toLowerCase
      val cyrillic =
        if (apostrophePatterns.contains((last, nextTwo))) '\'' else 'ь'
      val result = if (text(ofs - 2).isUpper) cyrillic.toUpper else cyrillic

      (0, result)
    } else {
      (0, text(ofs - 1))
    }
  }
}
