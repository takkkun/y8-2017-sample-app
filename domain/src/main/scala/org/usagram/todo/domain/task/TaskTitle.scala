package org.usagram.todo.domain.task

sealed trait TaskTitle {
  def value: String
}

private case class TaskTitleImpl(value: String) extends TaskTitle {
  override def toString: String =
    s"TaskTitle($value)"
}

object TaskTitle {
  def apply(value: String): TaskTitle = {
    if (value == "") {
      throw new TaskTitleCannotBeBlank
    }

    if (value.length > 20) {
      throw new TaskTitleTooLong(value)
    }

    TaskTitleImpl(value)
  }
}

class TaskTitleCannotBeBlank extends Exception("task title cannot be blank")

class TaskTitleTooLong(val value: String) extends Exception(s"task title is too long: $value")
