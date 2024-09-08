package adapter.routers.security

import adapter.context.UserContext
import adapter.controllers.AuthenticationController
import adapter.json.writes.{JsValueAuthenticationFailed, JsValueError, JsValueInternalServerError}
import sttp.model.StatusCode
import sttp.tapir.*
import sttp.tapir.json.play.jsonBody
import sttp.tapir.server.PartialServerEndpoint

import javax.inject.Inject
import scala.concurrent.Future

class SecureEndpoints @Inject() (
    authenticationController: AuthenticationController
):

  private val secureEndpointWithBearer: Endpoint[String, Unit, JsValueError, Unit, Any] =
    endpoint
      .securityIn(auth.bearer[String]())
      .errorOut(
        oneOf[JsValueError](
          oneOfVariant(statusCode(StatusCode.Unauthorized).and(jsonBody[JsValueAuthenticationFailed])),
          oneOfDefaultVariant(statusCode(StatusCode.InternalServerError).and(jsonBody[JsValueInternalServerError]))
        )
      )

  val authenticationWithBearerEndpoint: PartialServerEndpoint[String, UserContext, Unit, JsValueError, Unit, Any, Future] =
    secureEndpointWithBearer
      .serverSecurityLogic(authenticationController.authenticateWithBearer)
