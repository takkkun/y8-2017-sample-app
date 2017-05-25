package org.usagram.todo.application.task

import org.usagram.todo.application._
import org.usagram.todo.infrastructure.jdbc._

import scalikejdbc._

trait GetTaskStatisticsQuery

object GetTaskStatisticsQuery extends GetTaskStatisticsQuery

case class GetTaskStatisticsDto(
  numberOfTasks:           Int,
  numberOfUnfinishedTasks: Int,
  numberOfFinishedTasks:   Int
)

object GetTaskStatisticsApplicationService extends ApplicationService[GetTaskStatisticsQuery, GetTaskStatisticsDto] {
  def apply(query: GetTaskStatisticsQuery): GetTaskStatisticsDto =
    DB readOnly { implicit session =>
      val t = TaskTable.syntax("t")

      withSQL {
        select(sqls.count(t), sqls.count(t.finishedAt)).from(TaskTable as t)
      }.map { rs =>
        val numberOfTasks = rs.int(1)
        val numberOfFinishedTasks = rs.int(2)

        GetTaskStatisticsDto(
          numberOfTasks,
          numberOfTasks - numberOfFinishedTasks,
          numberOfFinishedTasks
        )
      }.single.apply.getOrElse(GetTaskStatisticsDto(0, 0, 0))
    }
}
