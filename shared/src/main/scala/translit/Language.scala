package translit

trait Language {
  /**
    * Convert Latin character `c` to Cyrillic
    *
    * @param latin     Latin input, excluding next character
    * @param cyrillic  Mapped Cyrillic letters
    * @param append    Latin letter to be mapped next
    *
    * @return (-n, r)  Replace last `n` characters by `r` with `left.length >= n`
    *         ( 0, r)  Append string `r`
    */
  def latinToCyrillicIncremental(
    latin: String, cyrillic: String, append: Char
  ): (Int, String)

  def latinToCyrillic(text: String): String = {
    val result = new StringBuilder(text.length)
    var offset = 0

    while (offset < text.length) {
      val (length, c) = latinToCyrillicIncremental(
        text.take(offset), result.mkString, text(offset))
      if (length < 0) result.setLength(result.length + length)
      result.append(c)
      offset += 1
    }

    result.mkString
  }
}
