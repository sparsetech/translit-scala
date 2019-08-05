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
    'z' -> 'з'
  )

  // Infer case from previous character
  val uniGramsSpecial = Map(
    '\'' -> 'ь',
    '`' -> 'ъ'
  )

  val biGrams = Map(
    "ch" -> 'ч',
    "sh" -> 'ш',
    "ya" -> 'я',
    "ye" -> 'э',
    "zh" -> 'ж',
    "yo" -> 'ё',
    "yu" -> 'ю',
    "kh" -> 'х'
  )

  val triGrams = Map[String, Char]()

  val fourGrams = Map(
    "shch" -> 'щ'
  )

  val uniGramsInv = uniGrams.toList.map(_.swap).toMap
  val uniGramsSpecialInv = uniGramsSpecial.toList.map(_.swap).toMap
  val biGramsInv = biGrams.toList.map(_.swap).toMap
  val triGramsInv = triGrams.toList.map(_.swap).toMap
  val fourGramsInv = fourGrams.toList.map(_.swap).toMap

  // y after m/n/r/t/v will be rendered as ы unless it is iotated
  val yLetters = Set("my", "ny", "ry", "ty", "vy")

  // If the y is iotated, render it as я, ё or ю
  val iotatedLetters = Set("ya", "yo", "yu")

  override def latinToCyrillicIncremental(
    latin: String, cyrillic: String, append: Char
  ): (Int, String) = {
    val text = latin + append
    val ofs = text.length
    val result =
      if (ofs >= 4 &&
          fourGrams.contains(text.substring(ofs - 4, ofs).toLowerCase)) {
        val chars = text.substring(ofs - 4, ofs)
        val cyrillic = fourGrams(chars.toLowerCase)
        (-2, restoreCaseFirst(chars, cyrillic).toString)
      } else if (ofs >= 3
        && yLetters.contains(text.substring(ofs - 3, ofs - 1).toLowerCase)
        && !iotatedLetters.contains(text.substring(ofs - 2, ofs).toLowerCase)
      ) {
        val cyrillic = uniGrams.getOrElse(text(ofs - 1).toLower, text(ofs - 1))
        (0, (if (text(ofs - 1).isUpper) cyrillic.toUpper else cyrillic).toString)
      } else if (ofs >= 3 &&
                 triGrams.contains(text.substring(ofs - 3, ofs).toLowerCase)) {
        val chars = text.substring(ofs - 3, ofs)
        val cyrillic = triGrams(chars.toLowerCase)
        (-2, restoreCaseFirst(chars, cyrillic).toString)
      } else if (ofs >= 2 &&
                 biGrams.contains(text.substring(ofs - 2, ofs).toLowerCase)) {
        val chars = text.substring(ofs - 2, ofs)
        val cyrillic = biGrams(chars.toLowerCase)
        (-1, restoreCaseFirst(chars, cyrillic).toString)
      } else if (uniGrams.contains(text(ofs - 1).toLower)) {
        val cyrillic = uniGrams(text(ofs - 1).toLower)
        (0, (if (text(ofs - 1).isUpper) cyrillic.toUpper else cyrillic).toString)
      } else if (ofs >= 2 && uniGramsSpecial.contains(text(ofs - 1))) {
        val result =
          if (ofs >= 3 && text(ofs - 2).isUpper && text(ofs - 3).isUpper)
            uniGramsSpecial(text(ofs - 1)).toUpper
          else uniGramsSpecial(text(ofs - 1))
        (0, result.toString)
      } else {
        (0, text(ofs - 1).toString)
      }

    if (ofs >= 3 && uniGramsSpecial.contains(text(ofs - 2))) {
      val (l, r) = (text(ofs - 3), text(ofs - 1))
      val letter = uniGramsSpecial(text(ofs - 2))
      val replace = if (l.isUpper && r.isUpper) letter.toUpper else letter
      val cyrillicOfs = cyrillic.length - 1

      if (replace == cyrillic(cyrillicOfs)) result
      else {
        val updated = replace + cyrillic.substring(
          cyrillicOfs + 1, cyrillic.length + result._1)
        (-updated.length + result._1, updated + result._2)
      }
    } else result
  }

  private def toLatin(letter: Char): String = {
    val isUpper = letter.isUpper
    val letterLc = letter.toLower
    fourGramsInv.get(letterLc).map(applyCase(_, isUpper))
      .orElse(triGramsInv.get(letterLc).map(applyCase(_, isUpper)))
      .orElse(biGramsInv.get(letterLc).map(applyCase(_, isUpper)))
      .orElse(uniGramsInv.get(letterLc).map(x => applyCase(x.toString, isUpper)))
      .orElse(uniGramsSpecialInv.get(letterLc).map(x => applyCase(x.toString, isUpper)))
      .getOrElse(letter.toString)
  }

  override def cyrillicToLatinIncremental(
    cyrillic: String, letter: Char
  ): (Int, String) = {
    val current = toLatin(letter)

    val changeCase =
      letter.isUpper &&
      (cyrillic.length == 1 || cyrillic.lastOption.exists(_.isUpper))

    if (!changeCase) (0, current)
    else {
      val mapped = toLatin(cyrillic.last)
      val rest = mapped.tail
      (-rest.length, rest.toUpperCase + current.toUpperCase)
    }
  }
}
