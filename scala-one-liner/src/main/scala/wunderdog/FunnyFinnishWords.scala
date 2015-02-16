package wunderdog

import scala.io.Source
import scala.math.pow

/**
 * Calcalate funniest finnish words according to algorithm and data provided in http://wunderdog.fi/koodaus-hassuimmat-sanat/
 * Optimizing LOC (not performance, testability, or any other dimension)
 * Input data is Volter Kilpi's novel "Alastalon Salissa" : http://en.wikipedia.org/wiki/Alastalon_salissa
 *
 * @author Ville Komulainen
 */
object FunnyFinnishWords extends App {
  Source.fromFile("src/test/resources/alastalon_salissa.txt")
    .getLines
    .flatMap(_.toLowerCase().split("[\\s+,\\.\\?]"))
    .toList
    .groupBy("([aeiouyöåäAEIOUYÖÄÅ]+)".r.findAllIn(_).map(vowelGroup => vowelGroup.length * pow(2, vowelGroup.length)).sum)
    .toList
    .sortBy(_._1)
    .reverse
    .head
    ._2
    .foreach(println)
}
