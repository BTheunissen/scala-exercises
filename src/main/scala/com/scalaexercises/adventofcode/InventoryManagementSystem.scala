package com.scalaexercises.adventofcode

import cats.implicits._

object InventoryManagementSystem {

  def getChecksum(boxIds: List[String]): Int = {
    val repeatingLetterCount = (limit: Int) => boxIds.count(boxId => hasRepeatingLetters(boxId, limit))

    repeatingLetterCount(2) * repeatingLetterCount(3)
  }

  private def hasRepeatingLetters(boxId: String, limit: Int): Boolean = {
    boxId.foldLeft(Map[Char, Int]().withDefaultValue(0)) { (acc, char) =>
      acc.updated(char, acc(char) + 1)
    }.foldLeft(false) { (acc, pair) =>
      pair._2 == limit || acc
    }
  }

  def getPrototypeLetters(boxIds: List[String]): Option[(String, String)] = {
    val charLocationMap = buildIdMap(boxIds)
    println(charLocationMap)

    def recurse(currIds: List[String]): Option[(String, String)] = {
      if (currIds.isEmpty) {
        None
      }
      else {
        checkDifferences(currIds.head, charLocationMap) match {
          case success @ Some(_) => success
          case None => recurse(currIds.tail)
        }
      }
    }

    recurse(boxIds)
  }

  private type CharLocationMap = Map[(Char, Int), List[String]]

  private def buildIdMap(boxIds: List[String]): CharLocationMap = {
    boxIds.map(id => id.zipWithIndex.map(_ -> List(id)).toMap).combineAll
  }

  private def checkDifferences(id: String, charLocationMap: CharLocationMap): Option[(String, String)] = {
    id.zipWithIndex.flatMap(char => charLocationMap(char).find(hasSingleCharSimilar(_, id))).headOption.map(id -> _)
  }

  private def hasSingleCharSimilar(A: String, B: String): Boolean = {
    A.zip(B).count(pair => pair._1 == pair._2) == 1
  }

  def main(args: Array[String]): Unit = {
    val lines = Iterator.continually(scala.io.StdIn.readLine()).takeWhile(x => x != "END").toList

    println(getPrototypeLetters(lines))
  }
}
