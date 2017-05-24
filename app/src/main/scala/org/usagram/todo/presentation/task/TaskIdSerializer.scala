package org.usagram.todo.presentation.task

import org.usagram.todo.domain.task._

import org.json4s._

import java.util.UUID

object TaskIdSerializer extends Serializer[TaskId] {
  private val TaskIdClass = classOf[TaskId]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), TaskId] = {
    case (TypeInfo(TaskIdClass, _), json) =>
      json match {
        case JString(string) => TaskId(UUID.fromString(string))
        case x               => throw new MappingException(s"cannot convert $x to TaskId")
      }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case taskId: TaskId => JString(taskId.value.toString)
  }
}
