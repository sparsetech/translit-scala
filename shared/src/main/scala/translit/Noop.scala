package translit

object Noop extends translit.Language {
  override def latinToCyrillicIncremental(
    latin: String, cyrillic: String, append: Char
  ): (Int, String) = (0, append.toString)

  override def cyrillicToLatinIncremental(
   cyrillic: String, letter: Char
  ): (Int, String) = (0, letter.toString)
}

