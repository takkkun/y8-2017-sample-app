package org.usagram.todo.presentation.task

import org.usagram.todo.presentation._
import org.usagram.todo.application.task._
import org.usagram.todo.domain.task._

import org.json4s._

object FinishTaskInbound extends ScalatraInbound[FinishTaskCommand] {
  implicit val jsonFormats: Formats = DefaultFormats ++ Seq(TaskIdSerializer)

  case class Body(id: TaskId)

  def apply(request: ScalatraRequest): FinishTaskCommand = {
    val body = request.bodyAsJson.extract[Body]
    FinishTaskCommand(body.id)
  }
}
