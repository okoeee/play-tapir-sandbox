package adapter.controllers

import adapter.json.writes
import adapter.json.reads

import domain.service.{TodoQueryService, TodoCommandService}

import javax.inject.*
import play.api.*
import play.api.mvc.*

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TodoController @Inject() (
    val controllerComponents: ControllerComponents,
    todoQueryService:         TodoQueryService,
    todoCommandService:       TodoCommandService
)(using ExecutionContext)
    extends BaseController:

  def get(id: Long): Future[Either[writes.JsValueError, writes.JsValueTodo]] =
    for {
      todoResult <- todoQueryService.get(id)
    } yield todoResult
      .map(todo => writes.JsValueTodo(todo))
      .left
      .map(error => writes.JsValueError.notFound(code = error.code, resource = error.resource))

  def create(jsValueTodo: reads.JsValueTodo): Future[Either[writes.JsValueError, Unit]] =
    val todoEntity = jsValueTodo.toTodoEntity
    todoEntity match {
      case Left(error) => Future.successful(Left(writes.JsValueError.validationError(error.code, error.details)))
      case Right(todo) => todoCommandService.add(todo).map(_ => Right(()))
    }
