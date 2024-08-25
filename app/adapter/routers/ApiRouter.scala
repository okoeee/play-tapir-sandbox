package adapter.routers

import adapter.routers.todo.Endpoints
import org.apache.pekko.stream.Materializer
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import sttp.tapir.server.play.{PlayServerInterpreter, PlayServerOptions}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class ApiRouter @Inject() (
    todoEndpoints:    Endpoints
)(implicit
    val materializer: Materializer,
    val ec:           ExecutionContext
) extends SimpleRouter:
  override def routes: Routes = {
    todoRoute
  }

  private val playServerOptions = PlayServerOptions.default(materializer, ec)
  private val interpreter = PlayServerInterpreter(playServerOptions)

  private val todoRoute = interpreter.toRoutes(todoEndpoints.endpoints)
