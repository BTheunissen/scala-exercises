package com.scalaexercises.ctci.recursion

import org.specs2.mutable.Specification

object PowerSetSpec extends Specification {
  "This is a specification for the PowerSet problem, 8.4".txt

  "The 'from Set' method should" >> {
    "return an empty set when given an empty set" >> {
      PowerSet.fromSet(Set()) must beEqualTo(Set(Set()))
    }
    "return a correct power set for a trivial case" >> {
      PowerSet.fromSet(Set[Int](1, 2, 3)) must beEqualTo(Set(
        Set(1,2,3), Set(1,2), Set(1,3), Set(2,3), Set(1), Set(2), Set(3), Set()
      ))
    }
  }
}
