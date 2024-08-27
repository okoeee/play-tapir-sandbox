package adapter.routers.todo

import adapter.controllers.TodoController
import adapter.json.{reads, writes}
import sttp.model.StatusCode
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.play.*
import sttp.tapir.server.ServerEndpoint

import javax.inject.Inject
import scala.concurrent.Future

class Endpoints @Inject() (
    todoController: TodoController
):

  private val baseEndpoint =
    endpoint
      .in("api" / "todo")

  private val findTodoEndpoint: PublicEndpoint[Long, writes.JsValueError, writes.JsValueTodo, Any] =
    baseEndpoint.get
      .in(path[Long]("id"))
      .out(jsonBody[writes.JsValueTodo])
      .errorOut(jsonBody[writes.JsValueError])

  private val createTodoEndpoint: PublicEndpoint[reads.JsValueTodo, writes.JsValueError, Unit, Any] =
    baseEndpoint.post
      .in(jsonBody[reads.JsValueTodo])
      .errorOut(jsonBody[writes.JsValueError])
      .out(statusCode(StatusCode.NoContent))

  private val updateTodoEndpoint: PublicEndpoint[(Long, reads.JsValueTodo), writes.JsValueError, Unit, Any] =
    baseEndpoint.put
      .in(path[Long]("id"))
      .in(jsonBody[reads.JsValueTodo])
      .errorOut(jsonBody[writes.JsValueError])
      .out(statusCode(StatusCode.NoContent))

  private val deleteTodoEndpoint: PublicEndpoint[Long, writes.JsValueError, Unit, Any] =
    baseEndpoint.delete
      .in(path[Long]("id"))
      .errorOut(jsonBody[writes.JsValueError])

  val endpoints: List[ServerEndpoint[Any, Future]] = List(
    findTodoEndpoint.serverLogic(id => todoController.get(id)),
    createTodoEndpoint.serverLogic(jsValueTodo => todoController.create(jsValueTodo)),
    updateTodoEndpoint.serverLogic((id, jsValueTodo) => todoController.update(id, jsValueTodo)),
    deleteTodoEndpoint.serverLogicSuccess(id => todoController.delete(id))
  )
