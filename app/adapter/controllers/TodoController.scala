package adapter.controllers

import adapter.json.writes.{JsValueError, JsValueTodo}
import domain.service.TodoQueryService

import javax.inject.*
import play.api.*
import play.api.libs.json.Json
import play.api.mvc.*

import scala.concurrent.Future

@Singleton
class TodoController @Inject() (
    val controllerComponents: ControllerComponents,
    todoQueryService:         TodoQueryService
) extends BaseController:

  def findByIdOld(id: Long): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    todoQueryService
      .findById(id)
      .map { todo =>
        Ok(Json.toJson(JsValueTodo(todo)))
      } getOrElse Ok(Json.toJson(JsValueError.notFound("Todo")))
  }

  def findById(id: Long): Future[Either[JsValueError, JsValueTodo]] = {
    Future.successful {
      todoQueryService
        .findById(id)
        .map { todo =>
          JsValueTodo(todo)
        }
        .toRight(JsValueError.notFound("Todo"))
    }
  }
