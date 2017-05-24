package org.usagram.todo.presentation

import org.json4s._
import org.joda.time._

object DateTimeSerializer extends Serializer[DateTime] {
  private val DateTimeClass = classOf[DateTime]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), DateTime] = {
    case (TypeInfo(DateTimeClass, _), json) =>
      json match {
        case JString(string) => DateTime.parse(string)
        case x               => throw new MappingException(s"cannot convert $x to DateTime")
      }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case dateTime: DateTime => JString(dateTime.toString("yyyy-MM-dd'T'HH:mm:ssZZ"))
  }
}
