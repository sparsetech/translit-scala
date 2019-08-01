package translit

object Helpers {
  def applyCase(str: String, isUpper: Boolean): String =
    if (isUpper) str(0).toUpper + str.tail else str

  def restoreCaseAll(str: String, cyrillic: Char): Char =
    if (str.forall(_.isUpper)) cyrillic.toUpper else cyrillic

  def restoreCaseFirst(str: String, cyrillic: Char): Char =
    if (str(0).isUpper) cyrillic.toUpper else cyrillic
}
