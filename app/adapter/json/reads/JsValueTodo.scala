package adapter.json.reads

import domain.model.Todo
import domain.shared.ServiceError

case class JsValueTodo(
    title:       String,
    description: String,
    isDone:      Boolean
):
  def toTodoEntity: Either[ServiceError.ValidationFailed, Todo] =
    Todo(
      id = 0,
      title = this.title,
      description = this.description,
      isDone = this.isDone
    ).validate

object JsValueTodo:
  import play.api.libs.json.{Json, Reads, Format}
  given Format[JsValueTodo] = Json.format[JsValueTodo]
