import org.usagram.todo.presentation._

import org.scalatra._
import javax.servlet._
import scalikejdbc.config._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext): Unit = {
    DBs.setupAll
    context.mount(Endpoints, "/")
  }

  override def destroy(context: ServletContext): Unit = {
    DBs.closeAll
  }
}
