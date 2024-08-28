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

  private val baseEndpoint: Endpoint[Unit, Unit, Unit, Unit, Any] =
    endpoint
      .in("api" / "todo")
      .tag("Todo")

  private val findTodoEndpoint: PublicEndpoint[Long, writes.JsValueError, writes.JsValueTodo, Any] =
    baseEndpoint.get
      .in(path[Long]("id"))
      .out(jsonBody[writes.JsValueTodo])
      .errorOut(statusCode(StatusCode.NotFound).and(jsonBody[writes.JsValueError]))
      .summary("Todoの取得")
      .description("""
          |idを用いてTodoの取得を行う。
          |Todoが存在しない場合は404が返される。
         |""".stripMargin)

  private val createTodoEndpoint: PublicEndpoint[reads.JsValueTodo, writes.JsValueError, Unit, Any] =
    baseEndpoint.post
      .in(jsonBody[reads.JsValueTodo])
      .errorOut(statusCode(StatusCode.BadRequest).and(jsonBody[writes.JsValueError]))
      .out(statusCode(StatusCode.NoContent))
      .summary("Todoの作成")
      .description("""
          |Todoの作成を行う。
          |バリデーションに失敗した場合は400が返される。
          |""".stripMargin)

  private val updateTodoEndpoint: PublicEndpoint[(Long, reads.JsValueTodo), writes.JsValueError, Unit, Any] =
    baseEndpoint.put
      .in(path[Long]("id"))
      .in(jsonBody[reads.JsValueTodo])
      .errorOut(statusCode(StatusCode.BadRequest).and(jsonBody[writes.JsValueError]))
      .out(statusCode(StatusCode.NoContent))
      .summary("Todoの更新")
      .description("""
          |Todoの更新を行う。
          |バリデーションに失敗した場合は400が返される。
          |""".stripMargin)

  private val deleteTodoEndpoint: PublicEndpoint[Long, Unit, Unit, Any] =
    baseEndpoint.delete
      .in(path[Long]("id"))
      .summary("Todoの削除")
      .description("""
          |Todoの削除を行う。
          |""".stripMargin)

  val endpoints: List[ServerEndpoint[Any, Future]] = List(
    findTodoEndpoint.serverLogic(id => todoController.get(id)),
    createTodoEndpoint.serverLogic(jsValueTodo => todoController.create(jsValueTodo)),
    updateTodoEndpoint.serverLogic((id, jsValueTodo) => todoController.update(id, jsValueTodo)),
    deleteTodoEndpoint.serverLogicSuccess(id => todoController.delete(id))
  )
