package adapter.routers.todo

import adapter.context.UserContext
import adapter.controllers.{AuthorizationController, TodoController}
import adapter.json.{reads, writes}
import adapter.routers.security.SecureEndpoints
import sttp.model.StatusCode
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.play.*
import sttp.tapir.server.ServerEndpoint

import javax.inject.Inject
import scala.concurrent.Future

class Endpoints @Inject() (
    secureEndpoint:          SecureEndpoints,
    authorizationController: AuthorizationController,
    todoController:          TodoController
):

  private val baseEndpoint: Endpoint[Unit, Unit, Unit, Unit, Any] =
    endpoint
      .in("api" / "todo")
      .tag("Todo")

  private val findTodoEndpoint =
    secureEndpoint.authorizationWithBearerEndpoint.get
      .in("api" / "todo")
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

  private val createTodoEndpoint: PublicEndpoint[reads.JsValueTodo, writes.JsValueValidationError, Unit, Any] =
    baseEndpoint.post
      .in(jsonBody[reads.JsValueTodo])
      .errorOut(statusCode(StatusCode.BadRequest).and(jsonBody[writes.JsValueValidationError]))
      .out(statusCode(StatusCode.NoContent))
      .summary("Todoの作成")
      .description("""
          |Todoの作成を行う。
          |バリデーションに失敗した場合は400が返される。
          |""".stripMargin)

  private val updateTodoEndpoint: PublicEndpoint[(Long, reads.JsValueTodo), writes.JsValueValidationError, Unit, Any] =
    baseEndpoint.put
      .in(path[Long]("id"))
      .in(jsonBody[reads.JsValueTodo])
      .errorOut(statusCode(StatusCode.BadRequest).and(jsonBody[writes.JsValueValidationError]))
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
    findTodoEndpoint
      .serverLogic((context: UserContext) => (id: Long) => todoController.get(id)),
    createTodoEndpoint.serverLogic(jsValueTodo => todoController.create(jsValueTodo)),
    updateTodoEndpoint.serverLogic((id, jsValueTodo) => todoController.update(id, jsValueTodo)),
    deleteTodoEndpoint.serverLogicSuccess(id => todoController.delete(id))
  )
