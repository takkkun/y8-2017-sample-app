package org.usagram.todo.domain

trait Entity[ID <: Identifier] {
  def id: ID

  override def equals(obj: Any): Boolean =
    obj match {
      case that: Entity[_] => id == that.id
      case _               => false
    }

  override def hashCode(): Int =
    31 * id.##
}
