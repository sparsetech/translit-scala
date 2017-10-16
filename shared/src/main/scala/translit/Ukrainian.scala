package translit

object Ukrainian {
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
    'z' -> 'з'
  )

  val biGrams = Map(
    "ya" -> "я",
    "ye" -> "є",
    "yi" -> "ї",
    "yu" -> "ю",

    "ay" -> "ай",
    "ey" -> "ей",
    "iy" -> "ій",
    "yy" -> "ий",
    "yo" -> "йо",

    "ch" -> "ч",
    "kh" -> "х",
    "sh" -> "ш",
    "ts" -> "ц",
    "zh" -> "ж",
  )

  val triGrams = Map(
    "aya" -> "ая",
    "aye" -> "ає",
    "ayi" -> "аї",
    "ayu" -> "аю",

    "eya" -> "ея",
    "eye" -> "еє",
    "eyi" -> "еї",
    "eyu" -> "ею",

    "iya" -> "ія",
    "iye" -> "іє",
    "iyi" -> "ії",
    "iyu" -> "ію",

    "yya" -> "ия",
    "yye" -> "иє",
    "yyi" -> "иї",
    "yyu" -> "ию",

    "tsk" -> "цк",
    "zgh" -> "зг"
  )

  val fourGrams = Map(
    "shch" -> "щ"
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
      } else if (i + 2 <= text.length && biGrams.contains(text.substring(i, i + 2).toLowerCase)) {
        val cyrillic = biGrams(text.substring(i, i + 2).toLowerCase)
        result.append(restoreCase(text.substring(i, i + 2), cyrillic))
        i += 2
      } else if ('c' == text(i).toLower) {
        // Skip Latin `c` to avoid confusion as its Cyrillic counterpart has a
        // different byte code
        i += 1
      } else if (uniGrams.contains(text(i).toLower)) {
        val cyrillic = uniGrams(text(i).toLower)
        result.append(if (text(i).isUpper) cyrillic.toUpper else cyrillic)
        i += 1
      } else if (text(i) == '\'') {
        if (apostrophes) {
          val last     = if (i >= 1) text(i - 1).toLower else '\u0000'
          val nextTwo  = text.slice(i + 1, i + 3).toLowerCase
          val cyrillic =
            if (apostrophePatterns.contains((last, nextTwo))) '\'' else 'ь'

          result.append(
            if (i > 0 && text(i - 1).isUpper &&
              !(i == 1 || (i > 1 && text(i - 2).isWhitespace))
            ) cyrillic.toUpper else cyrillic)
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