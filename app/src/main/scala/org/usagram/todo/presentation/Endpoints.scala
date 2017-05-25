package org.usagram.todo.presentation

import org.usagram.todo.application._
import org.usagram.todo.application.task._
import org.usagram.todo.presentation.task._

import org.scalatra._
import org.scalatra.json._
import org.json4s._

object Endpoints extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats.preservingEmptyValues ++ Seq(
    DateTimeSerializer,
    TaskIdSerializer,
    TaskTitleSerializer
  )

  before() {
    contentType = formats("json")
  }

  post("/CreateTask") {
    run(CreateTaskApplicationService, CreateTaskInbound, CreateTaskOutbound)
  }

  post("/FinishTask") {
    run(FinishTaskApplicationService, FinishTaskInbound, FinishTaskOutbound)
  }

  get("/GetTaskList") {
    run(GetTaskListApplicationService, GetTaskListInbound, GetTaskListOutbound)
  }

  get("/GetUnfinishedTaskList") {
    run(GetUnfinishedTaskListApplicationService, GetUnfinishedTaskListInbound, GetUnfinishedTaskListOutbound)
  }

  get("/GetTaskStatistics") {
    run(GetTaskStatisticsApplicationService, GetTaskStatisticsInbound, GetTaskStatisticsOutbound)
  }

  private def run[A, B](applicationService: ApplicationService[A, B], inbound: ScalatraInbound[A], outbound: ScalatraOutbound[B]): ActionResult = {
    val request = new ScalatraRequest(this)
    outbound(applicationService(inbound(request)))
  }
}
