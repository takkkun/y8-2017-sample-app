package org.usagram.todo.application.task

import org.usagram.todo.application._
import org.usagram.todo.domain.task._
import org.usagram.todo.infrastructure.jdbc.task._

import scalikejdbc._

class FinishTaskCommand private (val id: TaskId)

object FinishTaskCommand {
  def apply(id: TaskId): FinishTaskCommand =
    new FinishTaskCommand(id)
}

object FinishTaskApplicationService extends ApplicationService[FinishTaskCommand, Unit] {
  val taskRepo = JDBCTaskRepository // should apply dependency injection originally

  def apply(command: FinishTaskCommand): Unit =
    DB localTx { implicit session =>
      val task = taskRepo.resolve(command.id)
      val finishedTask = task.finish()
      taskRepo.update(finishedTask)
    }
}
