package org.usagram.todo.domain.task

import org.usagram.todo.domain._

trait TaskRepository[X] extends Repository[TaskId, Task, X] {
  def resolve(id: TaskId)(implicit context: X): Task =
    find(id).getOrElse(throw new TaskNotFound(id))
}

class TaskNotFound(val id: TaskId) extends Exception(s"Task(${id.value}) is not found")
