package org.usagram.todo

import org.usagram.todo.presentation._
import org.usagram.todo.infrastructure.jdbc._

import org.scalatra.test.scalatest._
import org.scalatest._
import org.json4s._
import org.json4s.jackson._
import scalikejdbc._
import org.joda.time._

class CreateTaskSpec extends ScalatraSpec with UsingDatabase with BeforeAndAfter {
  addServlet(Endpoints, "/*")

  val now = DateTime.now

  before {
    DateTimeUtils.setCurrentMillisFixed(now.getMillis)
  }

  after {
    DateTimeUtils.setCurrentMillisSystem()

    DB localTx { implicit session =>
      applyUpdate { deleteFrom(TaskTable) }
    }
  }

  case class Body(id: String, title: String, createdAt: String)

  describe("POST /CreateTask") {
    implicit val jsonFormats: Formats = DefaultFormats

    implicit val dbSession: DBSession = AutoSession

    val reqHeaders = Map("Content-Type" -> "application/json")

    val reqBody = Serialization.write(Map("title" -> "a title"))

    it("responds 200 OK") {
      post("/CreateTask", reqBody, reqHeaders) {
        status should be(200)
      }
    }

    it("responds created task") {
      post("/CreateTask", reqBody, reqHeaders) {
        val resBody = Serialization.read[Body](body)
        resBody.title should be("a title")
        resBody.createdAt should be(now.toString("yyyy-MM-dd'T'HH:mm:ssZZ"))
      }
    }

    it("stores created task") {
      post("/CreateTask", reqBody, reqHeaders) {
        val t = TaskTable.syntax("t")

        val taskRecords = withSQL {
          select.from(TaskTable as t)
        }.map(TaskRecord(t.resultName)).list.apply

        taskRecords should have size 1
        taskRecords.head.title should be("a title")
        taskRecords.head.createdAt should be(now.withZone(DateTimeZone.UTC).toLocalDateTime)
        taskRecords.head.finishedAt should be(empty)
      }
    }
  }
}
