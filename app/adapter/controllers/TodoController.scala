package adapter.controllers

import adapter.json.writes
import adapter.json.reads
import domain.model.Todo
import domain.service.{TodoCommandService, TodoQueryService}

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

  def get(id: Long): Future[Either[writes.JsValueNotFound, writes.JsValueTodo]] =
    for todoResult <- todoQueryService.get(id)
    yield todoResult
      .map(todo => writes.JsValueTodo(todo))
      .left
      .map(error => writes.JsValueNotFound(message = error.resource))

  def create(jsValueTodo: reads.JsValueTodo): Future[Either[writes.JsValueValidationError, Unit]] =
    val todoEntity = jsValueTodo.toTodoEntity
    todoEntity match {
      case Left(error) => Future.successful(Left(writes.JsValueValidationError(error.details)))
      case Right(todo) => todoCommandService.add(todo).map(_ => Right(()))
    }

  def update(id: Long, jsValueTodo: reads.JsValueTodo): Future[Either[writes.JsValueValidationError, Unit]] =
    val todoEntity = jsValueTodo.toTodoEntityWithId(id)
    todoEntity match {
      case Left(error) => Future.successful(Left(writes.JsValueValidationError(error.details)))
      case Right(todo) => todoCommandService.update(todo).map(_ => Right(()))
    }

  def delete(id: Long): Future[Unit] =
    todoCommandService.delete(id)
