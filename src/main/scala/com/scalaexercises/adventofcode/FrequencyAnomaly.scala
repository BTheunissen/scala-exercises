package com.scalaexercises.adventofcode

import scala.io.StdIn

object FrequencyAnomaly {
  def detectAnomaly(frequencies: List[Int]): Int = frequencies.sum

  def detectRepeat(sourceFrequencies: List[Int]): Int = {
    def recurse(current: List[Int], acc: Int, seen: Set[Int]): Int = current match {
      case head :: tail =>
        val frequency = acc + head
        if (seen.contains(frequency))
          frequency
        else
          recurse(tail, frequency, seen + frequency)
      case Nil => recurse(sourceFrequencies, acc, seen)
    }

    recurse(sourceFrequencies, 0, Set())
  }


  def main(args: Array[String]): Unit = {
    println("Insert a series of anomalies")
    val inputLine = StdIn.readLine()
    println(detectRepeat(inputLine.split(',').map(_.filter(!_.isWhitespace).toInt).toList))
  }
}
