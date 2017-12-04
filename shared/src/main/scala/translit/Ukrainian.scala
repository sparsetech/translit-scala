package translit

import Helpers._

object Ukrainian extends Language {
  val uniGrams = Map(
    'a' -> 'а',
    'b' -> 'б',
    'd' -> 'д',
    'e' -> 'е',
    'f' -> 'ф',
    'g' -> 'ґ',
    'h' -> 'г',
    'i' -> 'і',
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
    'j' -> 'й',
    'z' -> 'з'
  )

  val biGrams = Map(
    "ya" -> 'я',
    "ye" -> 'є',
    "yi" -> 'ї',
    "yu" -> 'ю',

    "ch" -> 'ч',
    "kh" -> 'х',
    "sh" -> 'ш',
    "ts" -> 'ц',
    "zh" -> 'ж'
  )

  val triGrams = Map(
    "zgh" -> 'г'
  )

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

  /**
    * Converts one character starting from `offset`
    *
    * @return (-2, c)  Replace last two characters by `c`
    *         (-1, c)  Replace last character by `c`
    *         ( 0, c)  Append character `c`
    */
  def latinToCyrillicOfs(text: String,
                         offset: Int,
                         apostrophes: Boolean = true): (Int, Char) = {
    val ofs = offset + 1
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
      val nextTwo  = text.slice(ofs, ofs + 2).toLowerCase
      val cyrillic =
        if (apostrophePatterns.contains((last, nextTwo))) '\'' else 'ь'
      val result = if (text(ofs - 2).isUpper) cyrillic.toUpper else cyrillic

      (0, result)
    } else if ('c' == text(ofs - 1).toLower) {
      // Replace Latin `c` to avoid confusion as its Cyrillic counterpart has a
      // different byte code
      (0, 'ø')
    } else {
      (0, text(ofs - 1))
    }
  }
}