package org.usagram.todo.infrastructure.jdbc.task

import org.usagram.todo.infrastructure.jdbc._
import org.usagram.todo.domain.task._

import scalikejdbc._

object JDBCTaskRepository extends TaskRepository[DBSession] {
  def find(id: TaskId)(implicit context: DBSession): Option[Task] = {
    val t = TaskTable.syntax("t")

    withSQL {
      QueryDSL.select.from(TaskTable as t).where.eq(t.id, id)
    }.map { rs =>
      val taskRecord = TaskRecord(t.resultName)(rs)
      taskRecord.toDomainObject
    }.single.apply
  }

  def store(task: Task)(implicit context: DBSession): Unit = {
    val column = TaskTable.column

    applyUpdate {
      QueryDSL.insertInto(TaskTable)
        .namedValues(
          column.id -> task.id,
          column.title -> task.title,
          column.createdAt -> TimeZone.remove(task.createdAt),
          column.finishedAt -> task.finishedAt.map(TimeZone.remove)
        )
    }
  }

  def update(task: Task)(implicit context: DBSession): Unit = {
    val column = TaskTable.column

    applyUpdate {
      QueryDSL.update(TaskTable)
        .set(
          column.title -> task.title,
          column.createdAt -> TimeZone.remove(task.createdAt),
          column.finishedAt -> task.finishedAt.map(TimeZone.remove)
        )
        .where.eq(column.id, task.id)
    }
  }
}
