package translit

import translit.Helpers._

object Russian extends Language {
  val uniGrams = Map(
    'a' -> 'а',
    'b' -> 'б',
    'c' -> 'ц',
    'd' -> 'д',
    'e' -> 'е',
    'f' -> 'ф',
    'g' -> 'г',
    'h' -> 'х',
    'i' -> 'и',
    'j' -> 'й',
    'k' -> 'к',
    'l' -> 'л',
    'm' -> 'м',
    'n' -> 'н',
    'o' -> 'о',
    'p' -> 'п',
    'q' -> 'щ',
    'r' -> 'р',
    's' -> 'с',
    't' -> 'т',
    'u' -> 'у',
    'v' -> 'в',
    'w' -> 'ш',
    'x' -> 'ж',
    'y' -> 'ы',
    'z' -> 'з',
    '\'' -> 'ь',
    '"' -> 'ъ'
  )

  val biGrams = Map(
    "ch" -> 'ч',
    "sh" -> 'ш',
    "ya" -> 'я',
    "ye" -> 'э',
    "zh" -> 'ж',
    "yo" -> 'ё',
    "yu" -> 'ю',

    "y|" -> 'ы',  // красивые, выучил
    "s|" -> 'с'   // сходить
  )

  val triGrams = Map[String, Char]()

  val fourGrams = Map(
    "shch" -> 'щ'
  )

  def latinToCyrillicOne(left: String, c: Char, right: String): (Int, Char) = {
    val text = left + c
    val ofs = text.length
    if (ofs >= 4 &&
        fourGrams.contains(text.substring(ofs - 4, ofs).toLowerCase)) {
      val chars = text.substring(ofs - 4, ofs)
      val cyrillic = fourGrams(chars.toLowerCase)
      (-2, restoreCaseFirst(chars, cyrillic))
    } else if (ofs >= 3 &&
      triGrams.contains(text.substring(ofs - 3, ofs).toLowerCase)) {
      val chars = text.substring(ofs - 3, ofs)
      val cyrillic = triGrams(chars.toLowerCase)
      (-2, restoreCaseFirst(chars, cyrillic))
    } else if (ofs >= 2 &&
               biGrams.contains(text.substring(ofs - 2, ofs).toLowerCase)) {
      val chars = text.substring(ofs - 2, ofs)
      val cyrillic = biGrams(chars.toLowerCase)
      (-1, restoreCaseFirst(chars, cyrillic))
    } else if (uniGrams.contains(text(ofs - 1).toLower)) {
      val cyrillic = uniGrams(text(ofs - 1).toLower)
      (0, if (text(ofs - 1).isUpper) cyrillic.toUpper else cyrillic)
    } else {
      (0, text(ofs - 1))
    }
  }
}
