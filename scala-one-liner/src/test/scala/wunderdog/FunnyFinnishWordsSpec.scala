package wunderdog

import java.io.{ByteArrayOutputStream, PrintStream}

import org.scalatest.FlatSpec

class FunnyFinnishWordsSpec extends FlatSpec {

  "FunnyFinnishWords" should "print 6 funny words to stdout" in {
      // Hack to obtain printed words from stdout
      val stdoutStream = new ByteArrayOutputStream
      val stdoutRedirectStream = new PrintStream(stdoutStream)

      Console.withOut(stdoutRedirectStream)(FunnyFinnishWords.main(null))
      val funnyWordsOutput = stdoutStream.toString("UTF-8")
      val funnyWords = funnyWordsOutput.split(System.lineSeparator).toList

      assert(funnyWords.size == 6)
      assert(funnyWords.contains("seremoniioissa"))
      assert(funnyWords.contains("liioittelematta"))
      assert(funnyWords.contains("seremoniioilla"))
      assert(funnyWords.contains("puuaineen"))
      assert(funnyWords.contains("leeaakaan"))
      assert(funnyWords.contains("niiaamaan"))
    }
}
