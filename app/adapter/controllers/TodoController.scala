package adapter.controllers

import adapter.json.writes.{JsValueError, JsValueTodo}
import domain.service.TodoQueryService

import javax.inject.*
import play.api.*
import play.api.mvc.*

import scala.concurrent.Future

@Singleton
class TodoController @Inject() (
    val controllerComponents: ControllerComponents,
    todoQueryService:         TodoQueryService
) extends BaseController:

  def get(id: Long): Future[Either[JsValueError, JsValueTodo]] = {
    Future.successful {
      todoQueryService
        .get(id)
        .map { todo =>
          JsValueTodo(todo)
        }
        .toRight(JsValueError.notFound("Todo"))
    }
  }
