package translit

object Ukrainian {
  val uniGramPrefixes = Map(
    'y' -> 'й'
  )

  val uniGramInfixes = Map(
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
    'z' -> 'з'
  )

  val biGramPrefixes = Map(
    "ya" -> "я",
    "ye" -> "є",
    "yi" -> "ї",
    "yu" -> "ю"
  )

  val biGramInfixes = Map(
    "ch" -> "ч",
    "ia" -> "я",
    "ie" -> "є",
    "iu" -> "ю",
    "ji" -> "ї",
    "kh" -> "х",
    "sh" -> "ш",
    "ts" -> "ц",
    "zh" -> "ж",
    "ai" -> "ай",
    "iy" -> "ій",
    "ei" -> "ей",
    "yi" -> "ий"
  )

  val triGrams = Map(
    "aie" -> "ає",
    "aiu" -> "аю",
    "pie" -> "пє",
    "tsk" -> "цк",
    "zgh" -> "зг"
  )

  val fourGrams = Map(
    "shch" -> "щ"
  )

  def restoreCase(str: String, cyrillic: String): String =
    if (str.forall(_.isUpper)) cyrillic.toUpperCase
    else if (str(0).isUpper) cyrillic.capitalize
    else cyrillic

  def latinToCyrillic(text: String, apostrophes: Boolean = true): String = {
    val result = new StringBuilder(text.length)

    var i = 0
    while (i < text.length) {
      if (i + 4 <= text.length && fourGrams.contains(text.substring(i, i + 4).toLowerCase)) {
        val cyrillic = fourGrams(text.substring(i, i + 4).toLowerCase)
        result.append(restoreCase(text.substring(i, i + 4), cyrillic))
        i += 4
      } else if (i + 3 <= text.length && triGrams.contains(text.substring(i, i + 3).toLowerCase)) {
        val cyrillic = triGrams(text.substring(i, i + 3).toLowerCase)
        result.append(restoreCase(text.substring(i, i + 3), cyrillic))
        i += 3
      } else if (i + 2 <= text.length && biGramPrefixes.contains(text.substring(i, i + 2).toLowerCase) &&
          (i == 0 || (i - 1 >= 0 && !text(i - 1).isLetter))) {
        val cyrillic = biGramPrefixes(text.substring(i, i + 2).toLowerCase)
        result.append(restoreCase(text.substring(i, i + 2), cyrillic))
        i += 2
      } else if (i + 2 <= text.length && biGramInfixes.contains(text.substring(i, i + 2).toLowerCase)) {
        val cyrillic = biGramInfixes(text.substring(i, i + 2).toLowerCase)
        result.append(restoreCase(text.substring(i, i + 2), cyrillic))
        i += 2
      } else if (i + 2 <= text.length && uniGramPrefixes.contains(text(i).toLower) &&
          (i == 0 || (i - 1 >= 0 && !text(i - 1).isLetter))) {
        val cyrillic = uniGramPrefixes(text(i).toLower)
        result.append(if (text(i).isUpper) cyrillic.toUpper else cyrillic)
        i += 1
      } else if ('c' == text(i).toLower) {
        // Skip Latin `c` to avoid confusion as its Cyrillic counterpart has a
        // different byte code
        i += 1
      } else if (uniGramInfixes.contains(text(i).toLower)) {
        val cyrillic = uniGramInfixes(text(i).toLower)
        result.append(if (text(i).isUpper) cyrillic.toUpper else cyrillic)
        i += 1
      } else if (text(i) == '\'') {
        if (apostrophes) {
          val lastThree     = if (i >= 3) text.substring(i - 3, i) else ""
          val endApostrophe = Set('i', 'y')
          val cyrillic = if (i + 1 < text.length
            && lastThree != "ran"
            && endApostrophe.contains(text(i + 1).toLower)) '\'' else 'ь'

          result.append(
            if (i > 0 && text(i - 1).isUpper) cyrillic.toUpper else cyrillic)
        }

        i += 1
      } else {
        result.append(text(i))
        i += 1
      }
    }

    result.mkString
  }
}