package translit

trait Language {
  def latinToCyrillicOfs(text: String,
                         offset: Int,
                         apostrophes: Boolean = true): (Int, Char)

  def latinToCyrillic(text: String,
                      apostrophes: Boolean = true): String = {
    val result = new StringBuilder(text.length)
    var offset = 0

    while (offset < text.length) {
      val (length, c) = latinToCyrillicOfs(text, offset, apostrophes)
      if (length < 0) result.setLength(result.length + length)
      result.append(c)
      offset += 1
    }

    result.mkString
  }
}
