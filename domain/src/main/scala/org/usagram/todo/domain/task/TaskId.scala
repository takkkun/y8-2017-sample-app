package org.usagram.todo.domain.task

import org.usagram.todo.domain._

import java.util.UUID

sealed trait TaskId extends Identifier1[UUID]

private case class TaskIdImpl(_1: UUID) extends TaskId {
  override def toString =
    s"TaskId(${_1})"
}

object TaskId {
  def apply(_1: UUID): TaskId =
    TaskIdImpl(_1)

  def generate(): TaskId =
    apply(UUID.randomUUID)
}
