package adapter.routers.todo

import adapter.context.UserContext
import adapter.controllers.{AuthenticationController, TodoController}
import adapter.json.writes.JsValueError
import adapter.json.{reads, writes}
import adapter.routers.security.{SecureEndpoints, SecureEndpointsType}
import sttp.model.StatusCode
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.play.*
import sttp.tapir.server.ServerEndpoint

import javax.inject.Inject
import scala.concurrent.Future

class Endpoints @Inject() (
    secureEndpoint:           SecureEndpoints,
    authenticationController: AuthenticationController,
    todoController:           TodoController
):

  private val baseEndpoint: SecureEndpointsType.SecureEndpoint[Unit, writes.JsValueError, Unit] =
    secureEndpoint.authenticationWithBearerEndpoint
      .in("api" / "todo")
      .tag("Todo")

  private val findTodoEndpoint: SecureEndpointsType.SecureEndpoint[Long, writes.JsValueError, writes.JsValueTodo] =
    baseEndpoint.get
      .in(path[Long]("id"))
      .out(jsonBody[writes.JsValueTodo])
      .errorOutVariantPrepend(
        oneOfVariant(statusCode(StatusCode.NotFound).and(jsonBody[writes.JsValueNotFound]))
      )
      .summary("Todoの取得")
      .description("""
          |idを用いてTodoの取得を行う。
          |Todoが存在しない場合は404が返される。
         |""".stripMargin)

  private val createTodoEndpoint: SecureEndpointsType.SecureEndpoint[reads.JsValueTodo, writes.JsValueError, Unit] =
    baseEndpoint.post
      .in(jsonBody[reads.JsValueTodo])
      .errorOutVariantPrepend(
        oneOfVariant(statusCode(StatusCode.BadRequest).and(jsonBody[writes.JsValueValidationError]))
      )
      .out(statusCode(StatusCode.NoContent))
      .summary("Todoの作成")
      .description("""
          |Todoの作成を行う。
          |バリデーションに失敗した場合は400が返される。
          |""".stripMargin)

  private val updateTodoEndpoint: SecureEndpointsType.SecureEndpoint[(Long, reads.JsValueTodo), writes.JsValueError, Unit] =
    baseEndpoint.put
      .in(path[Long]("id"))
      .in(jsonBody[reads.JsValueTodo])
      .errorOutVariantPrepend(
        oneOfVariant(statusCode(StatusCode.BadRequest).and(jsonBody[writes.JsValueValidationError]))
      )
      .out(statusCode(StatusCode.NoContent))
      .summary("Todoの更新")
      .description("""
          |Todoの更新を行う。
          |バリデーションに失敗した場合は400が返される。
          |""".stripMargin)

  private val deleteTodoEndpoint: SecureEndpointsType.SecureEndpoint[Long, writes.JsValueError, Unit] =
    baseEndpoint.delete
      .in(path[Long]("id"))
      .errorOutVariantPrepend(
        oneOfVariant(statusCode(StatusCode.BadRequest).and(jsonBody[writes.JsValueBadRequest]))
      )
      .out(statusCode(StatusCode.NoContent))
      .summary("Todoの削除")
      .description("""
          |Todoの削除を行う。
          |""".stripMargin)

  val endpoints: List[ServerEndpoint[Any, Future]] = List(
    findTodoEndpoint
      .serverLogic((context: UserContext) => (id: Long) => todoController.get(id)),
    createTodoEndpoint
      .serverLogic((context: UserContext) => (jsValueTodo: reads.JsValueTodo) => todoController.create(jsValueTodo)),
    updateTodoEndpoint
      .serverLogic((context: UserContext) => (id: Long, jsValueTodo: reads.JsValueTodo) => todoController.update(id, jsValueTodo)),
    deleteTodoEndpoint
      .serverLogic((context: UserContext) => (id: Long) => todoController.delete(id))
  )
