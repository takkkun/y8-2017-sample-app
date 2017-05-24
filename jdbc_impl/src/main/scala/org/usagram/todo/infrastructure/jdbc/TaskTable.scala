package org.usagram.todo.infrastructure.jdbc

import scalikejdbc._

object TaskTable extends SQLSyntaxSupport[TaskRecord] {
  override val tableName = "tasks"
}
