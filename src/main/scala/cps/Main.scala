package cps

import cats._

sealed abstract class IO[+A] {}

object IO {
  final case class Pure[A](a: A) extends IO[A]
  final case class FlatMap[A, +B](io: IO[A], f: A => IO[B]) extends IO[B]
  final case class Start[A](io: IO[A]) extends IO[Fiber[A]]
  final case class AbortChannel[E, A](fa: Channel[E] => IO[A]) extends IO[A]

  sealed trait Progress[A, B]
  object Progress {
    final case class Cont[A, B](
        f: A => Either[B, IO[B]]
    ) extends Progress[A, B]
    final case class Identity[A]() extends Progress[A, A]
  }

  def unsafeRun[A](io: IO[A]): A = {
    def progress[B, C](
        io: IO[B],
        p: Progress[B, C]
    ): Either[C, Progress[B, C]] = {
      def go(b: B): Either[C, Progress[B, C]] = p match {
        case id: Progress.Identity[B] => Left(b: C)
        case c: Progress.Cont[B, C] =>
          c.f(b) match {
            case Left(b)   => Left(b)
            case Right(io) => progress[C, C](io, Progress.Identity[C]())
          }
      }

      io match {
        case Pure(b) => go(b)
        case bind: FlatMap[a, B] =>
          Right(Progress.Cont[a, B](bind.io, bind.f.andThen(Right(_))))
        case s: Start[a] =>
          s.io
      }
    }

    def loop[B](io: IO[B]): B = {
      progress(io) match {
        case Left(b) => b
        case Right(c: Cont[a, B]) =>
          c.f(loop[a](c.io)) match {
            case Left(b)   => b
            case Right(io) => loop(io)
          }
      }
    }

    loop(io)
  }
}

trait Fiber[A] {
  def join: IO[A]
}

trait Channel[E] {
  def abort(e: E): IO[Unit]

  def immune[A](f: IO ~> IO => IO[A]): IO[A]
}

object Main extends App {
  println(
    IO.unsafeRun {
      IO.FlatMap(
        IO.FlatMap(
          IO.Pure(2),
          (x: Int) => IO.Pure(x + 2)
        ),
        (x: Int) => IO.Pure(x + 2)
      )
    }
  )
  println("holla")
}
