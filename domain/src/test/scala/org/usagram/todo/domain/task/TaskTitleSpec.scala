package org.usagram.todo.domain.task

import org.scalatest._

class TaskTitleSpec extends FunSpec with Matchers {
  describe(".apply") {
    it("returns TaskTitle") {
      val taskTitle = TaskTitle("a title")
      taskTitle.value should be("a title")
    }

    describe("when value is empty") {
      it("throws TaskTitleCannotBeBlank") {
        intercept[TaskTitleCannotBeBlank] {
          TaskTitle("")
        }
      }
    }

    describe("when value is greater than 20 characters") {
      it("throws TaskTitleTooLong") {
        intercept[TaskTitleTooLong] {
          TaskTitle("x" * 21)
        }

        TaskTitle("x" * 20)
      }
    }
  }
}
