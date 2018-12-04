package com.scalaexercises.adventofcode

import java.time.{LocalDateTime, ZoneOffset}

import fastparse.Parsed.Success
import fastparse.NoWhitespace.noWhitespaceImplicit
import fastparse._

object SleepingGuards {

  trait GuardAction
  case class Begin(id: Int) extends GuardAction
  case object Sleep extends GuardAction
  case object Awaken extends GuardAction

  case class GuardEvent(timestamp: LocalDateTime, action: GuardAction, id: Option[Int])

  def number[_: P]: P[Int] = P(CharIn("0-9").rep(1).!.map(_.toInt))

  def timestamp[_: P]: P[LocalDateTime] =
    P("[" ~ number ~ "-" ~ number ~ "-" ~ number ~ " " ~ number ~ ":" ~ number ~ "]")
      .map {
        case (year: Int, month: Int, day: Int, hour: Int, minute: Int) =>
          LocalDateTime.of(year, month, day, hour, minute)
      }

  def guardBegin[_: P]: P[GuardAction] = P("Guard #" ~ number ~ " begins shift").map(Begin(_))

  def guardSleep[_: P]: P[GuardAction] = P("falls asleep".!.map(_ => Sleep))

  def guardAwaken[_: P]: P[GuardAction] = P("wakes up".!.map(_ => Awaken))

  def guardAction[_: P]: P[GuardAction] = P(guardBegin | guardSleep | guardAwaken)

  def guardEvent[_: P]: P[GuardEvent] = P(timestamp ~ " " ~ guardAction).map { case (timestamp, action) =>
    GuardEvent(timestamp, action, None)
  }

  def parseEvents(inputEvents: List[String]): List[GuardEvent] = {
    inputEvents.map(parse(_, guardEvent(_))).collect {
      case Success(claim, _) => claim
    }
  }

  def getSleepiestGuard(events: List[GuardEvent]): (Int, Int) = {
    val sortedEvents = events.sortBy(events => events.timestamp.toEpochSecond(ZoneOffset.UTC)
    val (eventsWithId, _) = sortedEvents.foldLeft((List[GuardEvent](), 0)) { (pair, event) =>
      val (list, currentId) = pair
      event match {
        case GuardEvent(timestamp, Begin(newId), _) => (list, newId)
        case event @ GuardEvent(_, _, _) => (list :+ event.copy(id = Some(currentId)), currentId)
      }
    }

    eventsWithId.grouped(2).map { case List(sleep, awaken) =>
      val difference = awaken.timestamp.toEpochSecond(ZoneOffset.UTC) - sleep.timestamp.toEpochSecond(ZoneOffset.UTC)
      (sleep.id, awaken.timestamp.toEpochSecond(ZoneOffset.UTC) - sleep.timestamp.toEpochSecond(ZoneOffset.UTC
      )
    }
  }

  def main(args: Array[String]): Unit = {
    val lines = Iterator.continually(scala.io.StdIn.readLine()).takeWhile(x => x != "END").toList

    println(parseEvents(lines))
  }


}
