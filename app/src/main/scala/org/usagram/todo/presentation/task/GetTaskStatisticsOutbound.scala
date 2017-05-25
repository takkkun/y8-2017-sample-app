package org.usagram.todo.presentation.task

import org.usagram.todo.presentation._
import org.usagram.todo.application.task._

import org.scalatra._

object GetTaskStatisticsOutbound extends ScalatraOutbound[GetTaskStatisticsDto] {
  case class Body(
    numberOfTasks:           Int,
    numberOfUnfinishedTasks: Int,
    numberOfFinishedTasks:   Int
  )

  def apply(dto: GetTaskStatisticsDto): ActionResult = {
    val body = Body(dto.numberOfTasks, dto.numberOfUnfinishedTasks, dto.numberOfFinishedTasks)
    Ok(body)
  }
}
