package translit

import translit.Helpers._

object Russian extends Language {
  val uniGrams = Map(
    'a' -> 'а',
    'b' -> 'б',
    'v' -> 'в',
    'g' -> 'г',
    'd' -> 'д',
    'e' -> 'е',
    'z' -> 'з',
    'i' -> 'и',
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
    'f' -> 'ф',
    'x' -> 'х',
    'h' -> 'х',
    'c' -> 'ц',
    'w' -> 'щ',
    '#' -> 'ъ',
    'y' -> 'ы'
  )

  val biGrams = Map(
    "jo" -> 'ё',
    "yo" -> 'ё',
    "zh" -> 'ж',
    "ch" -> 'ч',
    "sh" -> 'ш',
    "ye" -> 'э',
    "yu" -> 'ю',
    "ju" -> 'ю',
    "ya" -> 'я',
    "ja" -> 'я'
  )

  val triGrams = Map(
    "shh" -> 'щ'
  )

  // tried to use prefix rules but there are many exceptions in Russian language
  // Ex.: фольклор, пальцем
  val apostropheSuffix = Set(
    "ya",
    "ja",
    "yo",
    "jo",
    "i",
    "e",
    "yu",
    "yu",
    "",
  )

  val apostrophePrefix = Set(
    "b",
    "v",
    "d",
    "z",
    "k",
    "l",
    "m",
    "n",
    "p",
    "r",
    "c",
    "t",
    "sh"
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
    if (ofs >= 3 &&
        triGrams.contains(text.substring(ofs - 3, ofs).toLowerCase)) {
      val chars = text.substring(ofs - 3, ofs)
      val cyrillic = triGrams(chars.toLowerCase)
      (-1, restoreCaseAll(chars, cyrillic))
    } else if (ofs >= 2 &&
               biGrams.contains(text.substring(ofs - 2, ofs).toLowerCase)) {
      val chars = text.substring(ofs - 2, ofs)
      val cyrillic = biGrams(chars.toLowerCase)
      (-1, restoreCaseFirst(chars, cyrillic))
    } else if (uniGrams.contains(text(ofs - 1).toLower)) {
      val cyrillic = uniGrams(text(ofs - 1).toLower)
      (0, if (text(ofs - 1).isUpper) cyrillic.toUpper else cyrillic)
    } else if (text(ofs - 1) == '\'' && apostrophes && (
                 apostrophePrefix.contains(text.slice(ofs - 3, ofs - 1)) ||
                 apostrophePrefix.contains(text.slice(ofs - 2, ofs - 1))
               )) {
      if (text(ofs - 2).isUpper) (0, 'Ь') else (0, 'ь')
    } else {
      (0, text(ofs - 1))
    }
  }
}
