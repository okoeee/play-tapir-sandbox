package adapter.controllers

import adapter.json.writes.{JsValueError, JsValueTodo}
import domain.service.TodoQueryService

import javax.inject.*
import play.api.*
import play.api.mvc.*

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TodoController @Inject() (
    val controllerComponents: ControllerComponents,
    todoQueryService:         TodoQueryService
)(implicit ec: ExecutionContext)
    extends BaseController:

  def get(id: Long): Future[Either[JsValueError, JsValueTodo]] =
    for {
      todo <- todoQueryService.get(id)
    } yield {
      todo
        .map { todo =>
          JsValueTodo(todo)
        }
        .toRight(JsValueError.notFound("Todo"))
    }
