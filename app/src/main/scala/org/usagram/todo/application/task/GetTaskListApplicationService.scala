package org.usagram.todo.application.task

import org.usagram.todo.application._
import org.usagram.todo.domain.task._
import org.usagram.todo.infrastructure.jdbc._

import scalikejdbc._

trait GetTaskListQuery

object GetTaskListQuery extends GetTaskListQuery

object GetTaskListApplicationService extends ApplicationService[GetTaskListQuery, Seq[Task]] {
  def apply(query: GetTaskListQuery): Seq[Task] =
    DB readOnly { implicit session =>
      val t = TaskTable.syntax("t")

      withSQL {
        select.from(TaskTable as t).orderBy(t.createdAt.desc)
      }.map { rs =>
        val taskRecord = TaskRecord(t.resultName)(rs)
        taskRecord.toDomainObject
      }.list.apply
    }
}
