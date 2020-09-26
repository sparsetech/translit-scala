package translit

import java.nio.charset.Charset

import better.files._
import org.scalatest.funsuite.AnyFunSuite
import scalaj.http.Http

class CorpusSpec extends AnyFunSuite {
  def getDump(name: String): File = {
    val file = File(s"/tmp/$name")
    if (file.exists) file
    else {
      println(s"Downloading $name...")
      val http = Http(s"http://builds.sparse.tech/wiki/$name").asBytes
      assert(http.isSuccess)
      file.writeByteArray(http.body)
    }
  }

  def evaluate(dump: String, language: Language): (Long, Long) = {
    val sentences = getDump(dump)

    var correct = 0L
    var total   = 0L

    sentences.gzipInputStream().foreach { gzip =>
      val words = gzip.lines(Charset.forName("UTF-8"))
      val latin = (('a' to 'z') ++ ('A' to 'Z')).toSet

      words.filter(!_.exists(latin.contains)).foreach { original =>
        val latin    = language.cyrillicToLatin(original)
        val cyrillic = language.latinToCyrillic(latin)
        if (original == cyrillic) {
          correct += 1
        } else {
          println(s"Mismatch: $original vs $cyrillic")
        }

        total += 1
      }
    }

    (correct, total)
  }

  def check(perf: (Long, Long), expectedMinimum: Double): Unit = {
    val (correct, total) = perf
    val accuracy = correct.toDouble / total

    println(s"Correct : $correct")
    println(s"Total   : $total")
    println(s"Accuracy: $accuracy")

    assert(accuracy > expectedMinimum)
  }

  test("Russian") {
    val perf = evaluate("tokens-ru-1710634.txt.gz", Russian)
    check(perf, 0.999907)
  }

  test("Ukrainian") {
    val perf = evaluate("tokens-uk-2000041.txt.gz", Ukrainian)
    check(perf, 0.999938)
  }
}
