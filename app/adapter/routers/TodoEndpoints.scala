package adapter.routers

import adapter.json.writes.{JsValueError, JsValueTodo}
import sttp.tapir.json.play.*
import sttp.tapir.generic.auto.*
import sttp.tapir.*

import javax.inject.Inject

class TodoEndpoints @Inject():

  val findBookEndpoint: PublicEndpoint[Long, JsValueError, JsValueTodo, Any] =
    endpoint.get
      .in("todo" / path[Long]("id"))
      .out(jsonBody[JsValueTodo])
      .errorOut(jsonBody[JsValueError])
