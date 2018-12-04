package com.scalaexercises.adventofcode

import com.scalaexercises.adventofcode.NoMatterHowYouSliceIt._
import org.specs2.mutable.Specification

object NoMatterHowYouSliceItSpec extends Specification {
  "This is a specification for Advent Of Code Day 3".txt

  "The solution object should" >> {

    val example =
      """
        |#1 @ 1,3: 4x4
        |#2 @ 3,1: 4x4
        |#3 @ 5,5: 2x2
        |
        """.stripMargin

    "be able to parse the given example" >> {
      val expectation = List(
        Claim(1,1,3,4,4),
        Claim(2,3,1,4,4),
        Claim(3,5,5,2,2)
      )

      NoMatterHowYouSliceIt.parseClaims(example.split('\n').toList) === expectation
    }

    "give the correct answer for the given example" >> {
      val expectation = 4

      NoMatterHowYouSliceIt.getOverlappingClaimCount(
        NoMatterHowYouSliceIt.parseClaims(example.split('\n').toList)
      ) === expectation
    }
  }
}

