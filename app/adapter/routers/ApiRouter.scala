package adapter.routers

import adapter.routers.todo.Endpoints
import org.apache.pekko.stream.Materializer
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import sttp.tapir.server.play.{PlayServerInterpreter, PlayServerOptions}
import sttp.tapir.swagger.bundle.SwaggerInterpreter

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ApiRouter @Inject() (
    todoEndpoints: Endpoints
)(using
    Materializer,
    ExecutionContext
) extends SimpleRouter:
  override def routes: Routes = {
    swaggerRoute
      .orElse(todoRoute)
  }

  private val playServerOptions = PlayServerOptions.default
  private val interpreter = PlayServerInterpreter(playServerOptions)

  private val todoRoute = interpreter.toRoutes(todoEndpoints.endpoints)

  private val swaggerEndpoints = SwaggerInterpreter().fromServerEndpoints[Future](todoEndpoints.endpoints, "play-tapir-sandbox", "1.0")
  private val swaggerRoute = interpreter.toRoutes(swaggerEndpoints)
