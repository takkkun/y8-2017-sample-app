package org.usagram.todo.infrastructure.jdbc

import org.joda.time._

object TimeZone {
  private val timeZone = DateTimeZone.UTC

  def append(localDateTime: LocalDateTime): DateTime =
    localDateTime.toDateTime(timeZone)

  def remove(dateTime: DateTime): LocalDateTime =
    dateTime.withZone(timeZone).toLocalDateTime
}
