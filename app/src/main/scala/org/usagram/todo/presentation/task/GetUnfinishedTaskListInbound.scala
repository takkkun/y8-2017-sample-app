package org.usagram.todo.presentation.task

import org.usagram.todo.presentation._
import org.usagram.todo.application.task._

object GetUnfinishedTaskListInbound extends ScalatraInbound[GetUnfinishedTaskListQuery] {
  def apply(request: ScalatraRequest): GetUnfinishedTaskListQuery =
    GetUnfinishedTaskListQuery
}
