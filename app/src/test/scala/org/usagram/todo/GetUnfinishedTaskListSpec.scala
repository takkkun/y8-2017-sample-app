package org.usagram.todo

import org.usagram.todo.presentation._
import org.usagram.todo.domain.task._
import org.usagram.todo.infrastructure.jdbc._
import org.usagram.todo.infrastructure.jdbc.task._

import org.scalatra.test.scalatest._
import org.scalatest._
import org.scalatest.OptionValues._
import org.json4s._
import org.json4s.jackson._
import scalikejdbc._
import org.joda.time._

class GetUnfinishedTaskListSpec extends ScalatraSpec with UsingDatabase with BeforeAndAfterAll {
  addServlet(Endpoints, "/*")

  val firstTaskId = TaskId.generate()

  val secondTaskId = TaskId.generate()

  val now = DateTime.now

  override def beforeAll(): Unit = {
    super.beforeAll()

    DB localTx { implicit session =>
      val column = TaskTable.column

      applyUpdate {
        insertInto(TaskTable).namedValues(
          column.id -> firstTaskId,
          column.title -> "task #1",
          column.createdAt -> now.minusHours(2).withZone(DateTimeZone.UTC).toLocalDateTime,
          column.finishedAt -> None
        )
      }

      applyUpdate {
        insertInto(TaskTable).namedValues(
          column.id -> secondTaskId,
          column.title -> "task #2",
          column.createdAt -> now.minusHours(1).withZone(DateTimeZone.UTC).toLocalDateTime,
          column.finishedAt -> now.withZone(DateTimeZone.UTC).toLocalDateTime
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

  type Body = Seq[TaskItem]

  case class TaskItem(id: String, title: String, createdAt: String)

  describe("GET /GetUnfinishedTaskList") {
    implicit val jsonFormats: Formats = DefaultFormats

    implicit val dbSession: DBSession = AutoSession

    it("responds 200 OK") {
      get("/GetUnfinishedTaskList") {
        status should be(200)
      }
    }

    it("responds task list") {
      get("/GetUnfinishedTaskList") {
        val resBody = Serialization.read[Body](body)
        resBody should have size 1
        resBody.head.title should be("task #1")
        resBody.head.createdAt should be(now.minusHours(2).withZone(DateTimeZone.UTC).toString("yyyy-MM-dd'T'HH:mm:ssZZ"))
      }
    }
  }
}
