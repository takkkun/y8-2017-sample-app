package org.usagram.todo.presentation

import org.scalatra._

trait ScalatraOutbound[A] extends (A => ActionResult)
