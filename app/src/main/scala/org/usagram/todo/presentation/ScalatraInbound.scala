package org.usagram.todo.presentation

trait ScalatraInbound[A] extends (ScalatraRequest => A)
