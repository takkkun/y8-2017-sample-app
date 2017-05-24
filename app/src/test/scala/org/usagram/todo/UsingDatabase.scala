package org.usagram.todo

import org.scalatest._
import scalikejdbc.config._

trait UsingDatabase extends Suite with BeforeAndAfterAll {
  override def beforeAll(): Unit = {
    DBs.setupAll
    super.beforeAll()
  }

  override def afterAll(): Unit = {
    super.afterAll()
    DBs.closeAll()
  }
}
