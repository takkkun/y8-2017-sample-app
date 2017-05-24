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

class FinishTaskSpec extends ScalatraSpec with UsingDatabase with BeforeAndAfter {
  addServlet(Endpoints, "/*")

  val taskId = TaskId.generate()

  val now = DateTime.now

  before {
    DB localTx { implicit session =>
      val column = TaskTable.column

      applyUpdate {
        insertInto(TaskTable).namedValues(
          column.id -> taskId,
          column.title -> "a title",
          column.createdAt -> now.minusHours(1).withZone(DateTimeZone.UTC).toLocalDateTime,
          column.finishedAt -> None
        )
      }
    }

    DateTimeUtils.setCurrentMillisFixed(now.getMillis)
  }

  after {
    DateTimeUtils.setCurrentMillisSystem()

    DB localTx { implicit session =>
      applyUpdate { deleteFrom(TaskTable) }
    }
  }

  describe("POST /FinishTask") {
    implicit val jsonFormats: Formats = DefaultFormats

    implicit val dbSession: DBSession = AutoSession

    val reqHeaders = Map("Content-Type" -> "application/json")

    val reqBody = Serialization.write(Map("id" -> taskId.value.toString))

    it("responds 204 NoContent") {
      post("/FinishTask", reqBody, reqHeaders) {
        status should be(204)
      }
    }

    it("updates the task") {
      post("/FinishTask", reqBody, reqHeaders) {
        val t = TaskTable.syntax("t")

        val taskRecords = withSQL {
          select.from(TaskTable as t)
        }.map(TaskRecord(t.resultName)).list.apply

        taskRecords should have size 1
        taskRecords.head.title should be("a title")
        taskRecords.head.createdAt should be(now.minusHours(1).withZone(DateTimeZone.UTC).toLocalDateTime)
        taskRecords.head.finishedAt.value should be(now.withZone(DateTimeZone.UTC).toLocalDateTime)
      }
    }
  }
}
