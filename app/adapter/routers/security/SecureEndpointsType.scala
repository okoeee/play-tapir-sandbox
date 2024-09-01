package adapter.routers.security

import adapter.context.UserContext
import sttp.tapir.server.PartialServerEndpoint

import scala.concurrent.Future

object SecureEndpointsType:
  /** @tparam I
    *   Input parameter type
    * @tparam E
    *   Error output parameter type
    * @tparam O
    *   Output parameter type
    */
  type SecureEndpoint[I, E, O] = PartialServerEndpoint[String, UserContext, I, E, O, Any, Future]
