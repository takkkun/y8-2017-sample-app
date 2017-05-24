package org.usagram.todo.infrastructure

import scalikejdbc._

import java.util.UUID

package object jdbc {
  implicit val uuidTypeBinder = TypeBinder[UUID](UUID fromString _.getString(_))(UUID fromString _.getString(_))
}
