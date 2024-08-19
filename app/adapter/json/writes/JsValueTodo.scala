package adapter.json.writes

case class JsValueTodo(
    id:          Long,
    title:       String,
    description: String,
    isDone:      Boolean
)
object JsValueTodo:
  import play.api.libs.json.{Json, Writes}
  given Writes[JsValueTodo] = Json.writes[JsValueTodo]

  def apply(todo: domain.model.Todo): JsValueTodo =
    JsValueTodo(todo.id, todo.title, todo.description, todo.isDone)
