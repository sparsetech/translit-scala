package translit

trait Language {
  def latinToCyrillicOfs(text: String,
                         offset: Int,
                         apostrophes: Boolean = true,
                         incremental: Boolean = false): (Int, Char)

  def latinToCyrillic(text: String,
                      apostrophes: Boolean = true,
                      incremental: Boolean = false): String = {
    val result = new StringBuilder(text.length)
    var offset = 0

    while (offset < text.length) {
      val (length, c) = latinToCyrillicOfs(text, offset, apostrophes, incremental)
      if (length < 0) result.setLength(result.length + length)
      result.append(c)
      offset += 1
    }

    result.mkString
  }

  def incrementalNgram(ngram: Map[String, Char]): Map[String, Char] =
    ngram.map { case (prefix, value) =>
      (latinToCyrillic(prefix.init) + prefix.last, value)
    }
}
