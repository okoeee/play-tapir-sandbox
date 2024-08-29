package adapter.json.writes

case class JsValueTodo(
    id:          Long,
    title:       String,
    description: String,
    isDone:      Boolean
)
object JsValueTodo:
  import play.api.libs.json.{Json, Format}
  given Format[JsValueTodo] = Json.format[JsValueTodo]

  def apply(todo: domain.model.Todo): JsValueTodo =
    JsValueTodo(todo.id, todo.title, todo.description, todo.isDone)
