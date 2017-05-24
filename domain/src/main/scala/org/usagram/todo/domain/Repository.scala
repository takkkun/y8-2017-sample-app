package org.usagram.todo.domain

trait Repository[ID <: Identifier, E <: Entity[ID], X] {
  def find(id: ID)(implicit context: X): Option[E]

  def store(entity: E)(implicit context: X): Unit

  def update(entity: E)(implicit context: X): Unit
}
