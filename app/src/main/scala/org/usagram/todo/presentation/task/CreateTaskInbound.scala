package org.usagram.todo.presentation.task

import org.usagram.todo.presentation._
import org.usagram.todo.application.task._
import org.usagram.todo.domain.task._

import org.json4s._

object CreateTaskInbound extends ScalatraInbound[CreateTaskCommand] {
  implicit val jsonFormats: Formats = DefaultFormats ++ Seq(TaskTitleSerializer)

  case class Body(title: TaskTitle)

  def apply(request: ScalatraRequest): CreateTaskCommand = {
    val body = request.bodyAsJson.extract[Body]
    CreateTaskCommand(body.title)
  }
}
