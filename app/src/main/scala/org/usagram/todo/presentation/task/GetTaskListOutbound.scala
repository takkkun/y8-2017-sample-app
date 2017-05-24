package org.usagram.todo.presentation.task

import org.usagram.todo.presentation._
import org.usagram.todo.domain.task._

import org.scalatra._
import org.joda.time._

object GetTaskListOutbound extends ScalatraOutbound[Seq[Task]] {
  case class TaskItem(id: TaskId, title: TaskTitle, createdAt: DateTime, finishedAt: Option[DateTime])

  def apply(tasks: Seq[Task]): ActionResult = {
    val taskItems = tasks.map { task => TaskItem(task.id, task.title, task.createdAt, task.finishedAt) }
    Ok(taskItems)
  }
}
