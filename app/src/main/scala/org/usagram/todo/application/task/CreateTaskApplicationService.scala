package org.usagram.todo.application.task

import org.usagram.todo.application._
import org.usagram.todo.domain.task._
import org.usagram.todo.infrastructure.jdbc.task._

import scalikejdbc._

class CreateTaskCommand private (val title: TaskTitle)

object CreateTaskCommand {
  def apply(title: TaskTitle): CreateTaskCommand =
    new CreateTaskCommand(title)
}

object CreateTaskApplicationService extends ApplicationService[CreateTaskCommand, Task] {
  val taskRepo = JDBCTaskRepository // should apply dependency injection originally

  def apply(command: CreateTaskCommand): Task =
    DB localTx { implicit session =>
      val task = Task.create(command.title)
      taskRepo.store(task)
      task
    }
}
