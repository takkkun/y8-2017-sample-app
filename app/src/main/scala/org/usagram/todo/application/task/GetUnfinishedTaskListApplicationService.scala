package org.usagram.todo.application.task

import org.usagram.todo.application._
import org.usagram.todo.domain.task._
import org.usagram.todo.infrastructure.jdbc._

import scalikejdbc._

trait GetUnfinishedTaskListQuery

object GetUnfinishedTaskListQuery extends GetUnfinishedTaskListQuery

object GetUnfinishedTaskListApplicationService extends ApplicationService[GetUnfinishedTaskListQuery, Seq[Task]] {
  def apply(query: GetUnfinishedTaskListQuery): Seq[Task] =
    DB readOnly { implicit session =>
      val t = TaskTable.syntax("t")

      withSQL {
        select.from(TaskTable as t).where.isNull(t.finishedAt).orderBy(t.createdAt.desc)
      }.map { rs =>
        val taskRecord = TaskRecord(t.resultName)(rs)
        taskRecord.toDomainObject
      }.list.apply
    }
}
