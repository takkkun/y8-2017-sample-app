package org.usagram.todo.presentation.task

import org.usagram.todo.presentation._
import org.usagram.todo.application.task._

object GetTaskStatisticsInbound extends ScalatraInbound[GetTaskStatisticsQuery] {
  def apply(request: ScalatraRequest): GetTaskStatisticsQuery =
    GetTaskStatisticsQuery
}
