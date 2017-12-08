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
    'x' -> 'х',
    'y' -> 'ы',
    'z' -> 'з',
    '"' -> 'ъ'
  )

  val biGrams = Map(
    "ch" -> 'ч',
    "sh" -> 'ш',
    "ya" -> 'я',
    "ye" -> 'э',
    "zh" -> 'ж',
    "yo" -> 'ё',
    "yu" -> 'ю'
  )

  val biGramsIncremental = getIncrementalNgram(biGrams)

  val triGrams = Map.empty[String, Char]
  val triGramsIncremental = Map(
    "шцh" -> 'щ'
  )

  val fourGrams = Map(
    "shch" -> 'щ'
  )
  val fourGramsIncremental = Map.empty[String, Char]

  def getIncrementalNgram(ngram: Map[String, Char]): Map[String, Char] = ngram ++ ngram.map { case (prefix, value) =>
    (latinToCyrillic(prefix.slice(0, prefix.length - 1), incrementalTranslit = true) + prefix.last, value)
  }

  /**
    * Converts one character starting from `offset`
    *
    * @return (-2, c)  Replace last two characters by `c`
    *         (-1, c)  Replace last character by `c`
    *         ( 0, c)  Append character `c`
    */
  def latinToCyrillicOfs(text: String,
                         offset: Int,
                         apostrophes: Boolean = true,
                         incrementalTranslit: Boolean = false): (Int, Char) = {
    val (biGramsL, triGramsL, fourGramsL) =
      if (incrementalTranslit) (biGramsIncremental, triGramsIncremental, fourGramsIncremental)
      else (biGrams, triGrams, fourGrams)
    val ofs = offset + 1
    if (ofs >= 4 &&
        fourGramsL.contains(text.substring(ofs - 4, ofs).toLowerCase)) {
      val chars = text.substring(ofs - 4, ofs)
      val cyrillic = fourGramsL(chars.toLowerCase)
      (-2, restoreCaseFirst(chars, cyrillic))
    } else if (ofs >= 3 &&
      triGramsL.contains(text.substring(ofs - 3, ofs).toLowerCase)) {
      val chars = text.substring(ofs - 3, ofs)
      val cyrillic = triGramsL(chars.toLowerCase)
      (-2, restoreCaseFirst(chars, cyrillic))
    } else if (ofs >= 2 &&
               biGramsL.contains(text.substring(ofs - 2, ofs).toLowerCase)) {
      val chars = text.substring(ofs - 2, ofs)
      val cyrillic = biGramsL(chars.toLowerCase)
      (-1, restoreCaseFirst(chars, cyrillic))
    } else if (uniGrams.contains(text(ofs - 1).toLower)) {
      val cyrillic = uniGrams(text(ofs - 1).toLower)
      (0, if (text(ofs - 1).isUpper) cyrillic.toUpper else cyrillic)
    } else if (text(ofs - 1) == '\'' && apostrophes) {
      if (text(ofs - 2).isUpper) (0, 'Ь') else (0, 'ь')
    } else {
      (0, text(ofs - 1))
    }
  }
}
