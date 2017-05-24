package org.usagram.todo.presentation.task

import org.usagram.todo.presentation._

import org.scalatra._

object FinishTaskOutbound extends ScalatraOutbound[Unit] {
  def apply(none: Unit): ActionResult =
    NoContent()
}
