package wd

import scala.math.pow
import java.net.URL

object FunnyWords extends App {

  val urlToRead = new URL("http://wunderdog.fi/koodaus-hassuimmat-sanat/alastalon_salissa.txt")
  val input = scala.io.Source.fromURL(urlToRead).mkString.replaceAll("[\\r\\n]", " ").toLowerCase.split(" ").toSet

  val wordAndValueSet = for (word <- input)
    yield {
      val cleanedUpWord = cleanUpWord(word)
      wordWithSpecialCharacters(word) -> calculateValue(cleanedUpWord)
    }

  val maxValue = wordAndValueSet.map(v => v._2).toList.max

  val wordAndValue = Map(wordAndValueSet.toList: _*)
  val wordsWithGreatesValue = wordAndValue.filter(wordWithValue => wordWithValue._2 == maxValue)

  println(s"$maxValue points for word(s)\n${wordsWithGreatesValue.keys.mkString(", ")}")

  def wordWithSpecialCharacters(word: String): String = {
    val unallowedCharacters = "[^a-z-'äö]"
    word.replaceAll(unallowedCharacters, "")
  }

  def cleanUpWord(word: String): String = {
    val onlyAlphabets = "[^a-zöä]"
    word.replaceAll(onlyAlphabets, "")
  }

  def calculateValue(word: String): Int = {
    val notVowel = "[^aeäoiöuy]"
    val onlyVowels = word.replaceAll(notVowel, " ").split(" ")
    val values = for (factor <- onlyVowels)
      yield (factor.length * pow(2, factor.length))
    values.sum.toInt
  }
}
