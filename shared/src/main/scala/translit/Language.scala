package translit

trait Language {
  def latinToCyrillicOfs(text: String,
                         offset: Int,
                         apostrophes: Boolean = true,
                         incrementalTranslit: Boolean = false): (Int, Char)

  def latinToCyrillic(text: String,
                      apostrophes: Boolean = true,
                      incrementalTranslit: Boolean = false): String = {
    val result = new StringBuilder(text.length)
    var offset = 0

    while (offset < text.length) {
      val (length, c) = latinToCyrillicOfs(text, offset, apostrophes, incrementalTranslit)
      if (length < 0) result.setLength(result.length + length)
      result.append(c)
      offset += 1
    }

    result.mkString
  }
}
