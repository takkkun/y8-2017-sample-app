package org.usagram.todo.application

trait ApplicationService[A, B] extends (A => B)
