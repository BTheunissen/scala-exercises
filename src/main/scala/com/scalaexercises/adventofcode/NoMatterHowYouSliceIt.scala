package com.scalaexercises.adventofcode

import cats.implicits._
import fastparse.Parsed.Success
import fastparse._
import fastparse.SingleLineWhitespace._

object NoMatterHowYouSliceIt {

  case class Claim(id: Int, leftEdge: Int, topEdge: Int, width: Int, length: Int)

  def number[_: P]: P[Int] = P(CharIn("0-9").rep(1).!.map(_.toInt))

  def claim[_: P]: P[Claim] = P("#" ~ number ~ "@" ~ number ~ "," ~ number ~ ":" ~ number ~ "x" ~ number)
    .map(Function.tupled(Claim.apply _)(_))

  def parseClaims(inputClaims: List[String]): List[Claim] = {
    inputClaims.map(parse(_, claim(_))).collect {
      case Success(claim, _) => claim
    }
  }

  def getMapFromClaim(claim: Claim): Map[(Int, Int), Int] = {
    val positionList = for (
      x <- claim.leftEdge until (claim.leftEdge + claim.width);
      y <- claim.topEdge until (claim.topEdge + claim.length)
    ) yield (x, y)

    positionList.foldLeft(Map[(Int, Int), Int]()) { (acc, pos) =>
      acc.updated(pos, 1)
    }
  }

  def getMapWithIdsFromClaim(claim: Claim): Map[(Int, Int), List[Int]] = {
    val positionList = for (
      x <- claim.leftEdge until (claim.leftEdge + claim.width);
      y <- claim.topEdge until (claim.topEdge + claim.length) if x != y
    ) yield (x, y)

    positionList.foldLeft(Map[(Int, Int), List[Int]]().withDefaultValue(List[Int]())) { (acc, pos) =>
      acc.updated(pos, List(claim.id))
    }
  }

  def getOverlappingClaimCount(claims: List[Claim]): Int = {
    claims.map(getMapFromClaim).combineAll.count(pair => pair._2 > 1)
  }

  def getNonOverlappingClaim(claims: List[Claim]): Set[Int] = {
    val claimIdSet = (1 to claims.length).toSet
    claims.map(getMapWithIdsFromClaim).combineAll.foldLeft(claimIdSet) { (acc, pair) =>
      val ids = pair._2
      val overlappingIds = if (ids.length > 1) ids.toSet else Set()

      acc -- overlappingIds
    }
  }

  def main(args: Array[String]): Unit = {
    val lines = Iterator.continually(scala.io.StdIn.readLine()).takeWhile(x => x != "END").toList

    println(getOverlappingClaimCount(parseClaims(lines)))
  }
}
