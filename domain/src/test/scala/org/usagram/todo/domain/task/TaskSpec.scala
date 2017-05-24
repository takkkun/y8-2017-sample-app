package org.usagram.todo.domain.task

import org.scalatest._
import org.joda.time._

class TaskSpec extends FunSpec with Matchers {
  describe(".apply") {
    object SampleValues {
      val id = TaskId.generate()

      val title = TaskTitle("a title")

      val createdAt = DateTime.now

      val finishedAt = None
    }

    it("returns Task") {
      import SampleValues._
      val task = Task(id, title, createdAt, finishedAt)
      task.id should be(id)
      task.title should be(title)
      task.createdAt should be(createdAt)
      task.finishedAt should be(finishedAt)
    }
  }

  describe(".create") {
    object SampleValues {
      val title = TaskTitle("a title")
    }

    it("returns Task") {
      import SampleValues._
      val task = Task.create(title)
      task.title should be(title)
      task.finishedAt should be(empty)
    }
  }

  describe("#isUnfinished") {
    describe("when finishedAt is empty") {
      val task = Task(TaskId.generate(), TaskTitle("a title"), DateTime.now, None)
        .ensuring(_.finishedAt.isEmpty)

      it("returns true") {
        task.isUnfinished should be(true)
      }
    }

    describe("when finishedAt is not empty") {
      val task = Task(TaskId.generate(), TaskTitle("a title"), DateTime.now, Some(DateTime.now))
        .ensuring(_.finishedAt.nonEmpty)

      it("returns false") {
        task.isUnfinished should be(false)
      }
    }
  }

  describe("#isFinished") {
    describe("when finishedAt is empty") {
      val task = Task(TaskId.generate(), TaskTitle("a title"), DateTime.now, None)
        .ensuring(_.finishedAt.isEmpty)

      it("returns false") {
        task.isFinished should be(false)
      }
    }

    describe("when finishedAt is not empty") {
      val task = Task(TaskId.generate(), TaskTitle("a title"), DateTime.now, Some(DateTime.now))
        .ensuring(_.finishedAt.nonEmpty)

      it("returns true") {
        task.isFinished should be(true)
      }
    }
  }

  describe(".finish") {
    val task = Task(TaskId.generate(), TaskTitle("a title"), DateTime.now, None)

    it("returns finished Task") {
      val finishedTask = task.finish()
      finishedTask should be(task)
      finishedTask.isFinished should be(true)
    }

    describe("when isFinished is true") {
      val task = Task(TaskId.generate(), TaskTitle("a title"), DateTime.now, Some(DateTime.now))
        .ensuring(_.isFinished)

      it("throws TaskAlreadyFinished") {
        intercept[TaskAlreadyFinished] {
          task.finish()
        }
      }
    }
  }
}
