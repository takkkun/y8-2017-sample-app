package org.usagram.todo.presentation

import org.scalatra._
import org.scalatra.json._
import org.json4s._

class ScalatraRequest(servlet: ScalatraServlet with JsonSupport[_]) {
  private implicit val request = servlet.request

  def bodyAsJson: JValue =
    servlet.parsedBody
}
