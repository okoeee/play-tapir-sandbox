package adapter.controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class HomeController @Inject() (val controllerComponents: ControllerComponents) extends BaseController {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("ok")
  }
}
