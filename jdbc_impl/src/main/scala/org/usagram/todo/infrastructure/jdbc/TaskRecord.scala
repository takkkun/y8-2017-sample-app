package org.usagram.todo.infrastructure.jdbc

import org.usagram.todo.domain.task._

import scalikejdbc._
import org.joda.time._

import java.util.UUID

case class TaskRecord(
    id:         UUID,
    title:      String,
    createdAt:  LocalDateTime,
    finishedAt: Option[LocalDateTime]
) extends EntityEquality {
  override val entityIdentity = id

  def toDomainObject: Task =
    Task(TaskId(id), TaskTitle(title), TimeZone.append(createdAt), finishedAt.map(TimeZone.append))
}

object TaskRecord {
  def apply(resultName: ResultName[TaskRecord])(rs: WrappedResultSet): TaskRecord =
    TaskRecord(rs.get(resultName.id), rs.get(resultName.title), rs.get(resultName.createdAt), rs.get(resultName.finishedAt))
}
