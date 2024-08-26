package domain.model

import domain.shared.ServiceError

case class Todo(
    id:          Long,
    title:       String,
    description: String,
    isDone:      Boolean
):
  def validate: Either[ServiceError.ValidationFailed, Todo] =
    if (invalidTile) Left(ServiceError.ValidationFailed("Title is too long"))
    else Right(this)

  private def invalidTile: Boolean =
    this.title.isEmpty && this.title.length >= 40
