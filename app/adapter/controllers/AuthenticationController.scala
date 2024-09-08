package adapter.controllers

import adapter.context.UserContext
import adapter.json.writes.JsValueAuthenticationFailed

import javax.inject.Inject
import scala.concurrent.Future

class AuthenticationController @Inject() ():

  def authenticateWithBearer(bearer: String): Future[Either[JsValueAuthenticationFailed, UserContext]] =
    Future.successful {
      if (bearer == "hoge") Right(UserContext(1, "John"))
      else Left(JsValueAuthenticationFailed("Unauthorized"))
    }
