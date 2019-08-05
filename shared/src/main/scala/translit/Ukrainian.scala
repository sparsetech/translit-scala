package translit

import Helpers._

object Ukrainian extends Language {
  val uniGrams = Map(
    'a' -> 'а',
    'b' -> 'б',
    'c' -> 'ц',
    'd' -> 'д',
    'e' -> 'е',
    'f' -> 'ф',
    'g' -> 'г',
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
    'h' -> 'х',
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
    "zh" -> 'ж',

    "kh" -> 'х'
  )

  val triGrams = Map[String, Char]()

  val fourGrams = Map(
    "shch" -> 'щ'
  )

  val escape = Map(
    "ya" -> "иа",
    "ye" -> "ие",
    "yi" -> "иі",
    "yu" -> "иу",
    "g'" -> "г'",
    "shch" -> "шч"
  )
  val escapeCharacter = '\\'

  val uniGramsInv = uniGrams.toList.map(_.swap).toMap
  val uniGramsSpecialInv = Map(
    'ь' -> '`',
    '\'' -> '\''
  )
  val biGramsInv = biGrams.toList.map(_.swap).toMap
  val triGramsInv = triGrams.toList.map(_.swap).toMap
  val fourGramsInv = fourGrams.toList.map(_.swap).toMap

  override def latinToCyrillicIncremental(
    latin: String, cyrillic: String, append: Char
  ): (Int, String) = {
    val text = latin + append
    val ofs = text.length

    val result =
      if (ofs >= 5 && text.takeRight(5).toLowerCase == "sh\\ch") {
        val cyrillic = 'ч'
        val result = if (text(ofs - 1).isUpper) cyrillic.toUpper else cyrillic
        (-2, result.toString)
      } else if (ofs >= 4 &&
       fourGrams.contains(text.substring(ofs - 4, ofs).toLowerCase)
      ) {
        val chars    = text.substring(ofs - 4, ofs)
        val cyrillic = fourGrams(chars.toLowerCase)
        (-2, restoreCaseFirst(chars, cyrillic).toString)
      } else if (ofs >= 3 &&
        triGrams.contains(text.substring(ofs - 3, ofs).toLowerCase)
      ) {
        val chars    = text.substring(ofs - 3, ofs)
        val cyrillic = triGrams(chars.toLowerCase)
        (-1, restoreCaseAll(chars, cyrillic).toString)
      } else if (ofs >= 3 &&
        escape.keySet.contains(
          (text(ofs - 3).toString + text(ofs - 1)).toLowerCase
        ) && text(ofs - 2) == escapeCharacter
      ) {
        val cyrillic = uniGrams.getOrElse(text(ofs - 1).toLower, text(ofs - 1))
        val result = if (text(ofs - 1).isUpper) cyrillic.toUpper else cyrillic
        (-1, result.toString)
      } else if (ofs >= 2 &&
        biGrams.contains(text.substring(ofs - 2, ofs).toLowerCase)
      ) {
        val chars = text.substring(ofs - 2, ofs)
        val cyrillic = biGrams(chars.toLowerCase)
        (-1, restoreCaseFirst(chars, cyrillic).toString)
      } else if (uniGrams.contains(text(ofs - 1).toLower)) {
        val cyrillic = uniGrams(text(ofs - 1).toLower)
        val result = if (text(ofs - 1).isUpper) cyrillic.toUpper else cyrillic
        (0, result.toString)
      } else if (ofs >= 2 && text(ofs - 1) == '`') {
        val result =
          if (ofs >= 3 && text(ofs - 2).isUpper && text(ofs - 3).isUpper) "Ь"
          else "ь"
        (0, result)
      } else {
        (0, text(ofs - 1).toString)
      }

    if (ofs >= 3 && text(ofs - 2) == '`') {
      val (l, r) = (text(ofs - 3), text(ofs - 1))
      val replace = if (l.isUpper && r.isUpper) 'Ь' else 'ь'
      val softSign = cyrillic.length - 1

      if (cyrillic(softSign).toLower != 'ь' || replace == cyrillic(softSign))
        result
      else {
        val updated = replace + cyrillic.substring(
          softSign + 1, cyrillic.length + result._1)
        (-updated.length + result._1, updated + result._2)
      }
    } else if (ofs >= 4 && text(ofs - 3) == '`') {
      val (l, r) = (text(ofs - 4), text.substring(ofs - 2, ofs))
      val letter = 'ь'
      val replace = if (l.isUpper && r.head.isUpper) letter.toUpper else letter
      val softSign = cyrillic.length - 2

      if (replace == cyrillic(softSign)) result
      else {
        val updated = replace + cyrillic.substring(
          softSign + 1, cyrillic.length + result._1)
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
    val current  = toLatin(letter)
    val toEscape = cyrillic.lastOption
      .map(_.toLower.toString + letter.toLower)
      .exists(escape.values.toList.contains)

    if (toEscape) (0, escapeCharacter + current)
    else {
      val changeCase =
        letter.isUpper && {
          val withoutApostrophes = cyrillic.filter(_ != '\'')
          withoutApostrophes.length == 1 ||
          withoutApostrophes.lastOption.exists(_.isUpper)
        }

      if (!changeCase) (0, current)
      else {
        val mapped = toLatin(cyrillic.last)
        val rest   = mapped.tail
        (-rest.length, rest.toUpperCase + current.toUpperCase)
      }
    }
  }
}
