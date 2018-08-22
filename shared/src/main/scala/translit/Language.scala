package translit

trait Language {
  /**
    * Convert Latin character `c` to Cyrillic
    *
    * @param left  Left context prior to mapping
    * @param c     Character to be mapped
    * @param right Right context after mapping
    * @return (-n, r)  Replace last `n` characters by `r` with `left.length >= n`
    *         ( 0, r)  Append character `r`
    *         ( n, r)  Replace next `n` characters by `r` with `right.length >= n`
    */
  def latinToCyrillicOne(left: String = "",
                         c: Char,
                         right: String = "",
                         apostrophes: Boolean = true): (Int, Char)

  def latinToCyrillic(text: String,
                      apostrophes: Boolean = true): String = {
    val result = new StringBuilder(text.length)
    var offset = 0

    while (offset < text.length) {
      val (length, c) = latinToCyrillicOne(
        text.take(offset), text(offset), text.drop(offset + 1), apostrophes)
      if (length < 0) result.setLength(result.length + length)
      result.append(c)
      offset += 1
    }

    result.mkString
  }
}
