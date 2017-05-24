package org.usagram.todo.presentation.task

import org.usagram.todo.presentation._
import org.usagram.todo.application.task._

object GetTaskListInbound extends ScalatraInbound[GetTaskListQuery] {
  def apply(request: ScalatraRequest): GetTaskListQuery =
    GetTaskListQuery
}
