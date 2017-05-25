package org.usagram.todo

import org.usagram.todo.presentation._
import org.usagram.todo.domain.task._
import org.usagram.todo.infrastructure.jdbc._
import org.usagram.todo.infrastructure.jdbc.task._

import org.scalatra.test.scalatest._
import org.scalatest._
import org.json4s._
import org.json4s.jackson._
import scalikejdbc._
import org.joda.time._

class GetTaskStatisticsSpec extends ScalatraSpec with UsingDatabase with BeforeAndAfterAll {
  addServlet(Endpoints, "/*")

  val now = DateTime.now

  override def beforeAll(): Unit = {
    super.beforeAll()

    DB localTx { implicit session =>
      val column = TaskTable.column

      applyUpdate {
        insertInto(TaskTable).namedValues(
          column.id -> TaskId.generate(),
          column.title -> "task #1",
          column.createdAt -> now,
          column.finishedAt -> None
        )
      }

      applyUpdate {
        insertInto(TaskTable).namedValues(
          column.id -> TaskId.generate(),
          column.title -> "task #2",
          column.createdAt -> now,
          column.finishedAt -> now
        )
      }

      applyUpdate {
        insertInto(TaskTable).namedValues(
          column.id -> TaskId.generate(),
          column.title -> "task #3",
          column.createdAt -> now,
          column.finishedAt -> None
        )
      }
    }
  }

  override def afterAll(): Unit = {
    DB localTx { implicit session =>
      applyUpdate { deleteFrom(TaskTable) }
    }

    super.afterAll()
  }

  case class Body(numberOfTasks: Int, numberOfUnfinishedTasks: Int, numberOfFinishedTasks: Int)

  describe("GET /GetTaskStatistics") {
    implicit val jsonFormats: Formats = DefaultFormats

    implicit val dbSession: DBSession = AutoSession

    it("responds 200 OK") {
      get("/GetTaskStatistics") {
        status should be(200)
      }
    }

    it("responds task statistics") {
      get("/GetTaskStatistics") {
        val resBody = Serialization.read[Body](body)
        resBody.numberOfTasks should be(3)
        resBody.numberOfUnfinishedTasks should be(2)
        resBody.numberOfFinishedTasks should be(1)
      }
    }
  }
}
