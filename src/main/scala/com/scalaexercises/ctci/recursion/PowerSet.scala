package com.scalaexercises.ctci.recursion

object PowerSet {
  def fromSet[A](input: Set[A]): Set[Set[A]] =
    input.foldLeft(Set[Set[A]](input)) { (acc: Set[Set[A]], setValue: A) =>
      acc ++ fromSet(input - setValue)
    }
}
