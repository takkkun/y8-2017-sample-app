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

class GetTaskListSpec extends ScalatraSpec with UsingDatabase with BeforeAndAfterAll {
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

  case class TaskItem(id: String, title: String, createdAt: String, finishedAt: Option[String])

  describe("GET /GetTaskList") {
    implicit val jsonFormats: Formats = DefaultFormats

    implicit val dbSession: DBSession = AutoSession

    it("responds 200 OK") {
      get("/GetTaskList") {
        status should be(200)
      }
    }

    it("responds task list") {
      get("/GetTaskList") {
        val resBody = Serialization.read[Body](body)
        resBody should have size 2
        val Seq(task1, task2) = resBody

        task1.title should be("task #2")
        task1.createdAt should be(now.minusHours(1).withZone(DateTimeZone.UTC).toString("yyyy-MM-dd'T'HH:mm:ssZZ"))
        task1.finishedAt.value should be(now.withZone(DateTimeZone.UTC).toString("yyyy-MM-dd'T'HH:mm:ssZZ"))

        task2.title should be("task #1")
        task2.createdAt should be(now.minusHours(2).withZone(DateTimeZone.UTC).toString("yyyy-MM-dd'T'HH:mm:ssZZ"))
        task2.finishedAt should be(empty)
      }
    }
  }
}
