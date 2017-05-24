package org.usagram.todo.presentation.task

import org.usagram.todo.presentation._
import org.usagram.todo.domain.task._

import org.scalatra._
import org.joda.time._

object CreateTaskOutbound extends ScalatraOutbound[Task] {
  case class Body(id: TaskId, title: TaskTitle, createdAt: DateTime)

  def apply(task: Task): ActionResult = {
    val body = Body(task.id, task.title, task.createdAt)
    Ok(body)
  }
}
