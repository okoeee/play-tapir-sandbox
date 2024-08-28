package adapter.json.reads

import domain.model.Todo
import domain.shared.ServiceError

case class JsValueTodo(
    title:       String,
    description: String,
    isDone:      Boolean
):
  def toTodoEntity: Either[ServiceError.ValidationFailed, Todo] =
    Todo.applyForCreate(
      title,
      description,
      isDone
    )

  def toTodoEntityWithId(id: Long): Either[ServiceError.ValidationFailed, Todo] =
    Todo.applyWithId(
      id,
      title,
      description,
      isDone
    )

object JsValueTodo:
  import play.api.libs.json.{Json, Reads, Format}
  given Format[JsValueTodo] = Json.format[JsValueTodo]
