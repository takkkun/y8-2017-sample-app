package org.usagram.todo.domain

trait Identifier1[+A1] extends Identifier with Product1[A1] {
  def value: A1 = _1
}
