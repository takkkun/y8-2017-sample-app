package org.usagram.todo.domain.task

import org.usagram.todo.domain._

import org.joda.time._

class Task private (
    val id:         TaskId,
    val title:      TaskTitle,
    val createdAt:  DateTime,
    val finishedAt: Option[DateTime]
) extends Entity[TaskId] {
  def isUnfinished: Boolean =
    finishedAt.isEmpty

  def isFinished: Boolean =
    !isUnfinished

  def finish(): Task = {
    if (isFinished) {
      throw new TaskAlreadyFinished(this)
    }

    Task(id, title, createdAt, Some(DateTime.now))
  }

  override def toString: String =
    s"Task(id = $id, title = $title, createdAt = $createdAt, finishedAt = $finishedAt)"
}

object Task {
  def apply(id: TaskId, title: TaskTitle, createdAt: DateTime, finishedAt: Option[DateTime]): Task =
    new Task(id, title, createdAt, finishedAt)

  def create(title: TaskTitle): Task =
    apply(TaskId.generate(), title, DateTime.now, None)
}

class TaskAlreadyFinished(val task: Task) extends Exception(s"$task is already finished")
