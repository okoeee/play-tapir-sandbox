package adapter.routers

import adapter.controllers.TodoController
import org.apache.pekko.stream.Materializer
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import sttp.tapir.server.play.{PlayServerInterpreter, PlayServerOptions}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class ApiRouter @Inject() (
    todoController:   TodoController,
    todoEndpoints:    TodoEndpoints
)(implicit
    val materializer: Materializer,
    val ec:           ExecutionContext
) extends SimpleRouter:
  override def routes: Routes = {
    findTodoRoute
  }

  private val playServerOptions = PlayServerOptions.default(materializer, ec)
  private val interpreter = PlayServerInterpreter(playServerOptions)
  private val findTodoRoute = interpreter.toRoutes(
    todoEndpoints.findBookEndpoint.serverLogic(id => todoController.get(id))
  )
