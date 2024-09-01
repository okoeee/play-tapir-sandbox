package adapter.controllers

import adapter.context.UserContext
import adapter.json.writes.JsValueAuthorizationFailed

import javax.inject.Inject
import scala.concurrent.Future

class AuthorizationController @Inject() ():

  def authorizationWithBearer(bearer: String): Future[Either[JsValueAuthorizationFailed, UserContext]] =
    Future.successful {
      if (bearer == "hoge") Right(UserContext(1, "John"))
      else Left(JsValueAuthorizationFailed("Authorization failed"))
    }
