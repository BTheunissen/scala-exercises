package com.scalaexercises.leetcode

import cats.implicits._

object WordSearch {
  def exist(board: Array[Array[Char]], word: String): Boolean = {
    def optGetLocation(location: (Int, Int)): List[(Int, Int)] =
      board.lift(location._2).flatMap(_.lift(location._1)).map(_ => location).toList

    def getChar(location: (Int, Int)): Char = board(location._2)(location._1)

    def getSurroundingChars(location: (Int, Int), visited: Set[(Int, Int)]): List[(Int, Int)] = {
      List((location._1 - 1, location._2),
        (location._1 + 1, location._2),
        (location._1, location._2 - 1),
        (location._1, location._2 + 1))
        .filter(!visited.contains(_))
        .flatMap(optGetLocation)
    }

    def findInitialLocations(char: Char): List[(Int, Int)] =
      (for {
        j <- board.indices
        i <- board.head.indices
        if board(j)(i) === char
      } yield (j, i)).toList


    def recurse(location: (Int, Int), visited: Set[(Int, Int)], word: String): Boolean = {
      if (word.isEmpty)
        true
      else {
        val results = getSurroundingChars(location, visited).filter(getChar(_) === word.head)
        results.foldLeft(false) { (result: Boolean, location: (Int, Int)) =>
          if (result)
            result
          else
            recurse(location, visited + location, word.tail)
        }
      }
    }

    val initialLocations = findInitialLocations(word.head)
    initialLocations.foldLeft(false) { (result: Boolean, location: (Int, Int)) =>
      if (result)
        result
      else
        recurse(location, Set(location), word.tail)
    }
  }
}
