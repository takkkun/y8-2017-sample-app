package org.usagram.todo.infrastructure.jdbc

import org.usagram.todo.domain.task._

import scalikejdbc._

package object task {
  implicit val taskIdParameterBinderFactory = ParameterBinderFactory[TaskId] { taskId =>
    _.setObject(_, taskId.value)
  }

  implicit val taskTitleParameterBinderFactory = ParameterBinderFactory[TaskTitle] { taskTitle =>
    _.setString(_, taskTitle.value)
  }
}
