package org.usagram.todo.presentation.task

import org.usagram.todo.presentation._
import org.usagram.todo.domain.task._

import org.scalatra._
import org.joda.time._

object GetUnfinishedTaskListOutbound extends ScalatraOutbound[Seq[Task]] {
  case class TaskItem(id: TaskId, title: TaskTitle, createdAt: DateTime)

  def apply(tasks: Seq[Task]): ActionResult = {
    val taskItems = tasks.map { task => TaskItem(task.id, task.title, task.createdAt) }
    Ok(taskItems)
  }
}
