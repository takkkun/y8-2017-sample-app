package org.usagram.todo.presentation.task

import org.usagram.todo.domain.task._

import org.json4s._

object TaskTitleSerializer extends Serializer[TaskTitle] {
  private val TaskTitleClass = classOf[TaskTitle]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), TaskTitle] = {
    case (TypeInfo(TaskTitleClass, _), json) =>
      json match {
        case JString(string) => TaskTitle(string)
        case x               => throw new MappingException(s"cannot convert $x to TaskTitle")
      }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case taskTitle: TaskTitle => JString(taskTitle.value)
  }
}
