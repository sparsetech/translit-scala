package translit

object Helpers {
  def restoreCaseAll(str: String, cyrillic: Char): Char =
    if (str.forall(_.isUpper)) cyrillic.toUpper else cyrillic

  def restoreCaseFirst(str: String, cyrillic: Char): Char =
    if (str(0).isUpper) cyrillic.toUpper else cyrillic
}
