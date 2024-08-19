package domain.model

case class Todo(
    id:          Long,
    title:       String,
    description: String,
    isDone:      Boolean
)
