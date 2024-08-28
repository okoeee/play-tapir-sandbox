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

object Todo:

  def applyWithId(
      id: Long,
      title: String,
      description: String,
      isDone: Boolean
  ): Either[ServiceError.ValidationFailed, Todo] =
    Todo(
      id = id,
      title = title,
      description = description,
      isDone = isDone
    ).validate

  def applyForCreate(
      title: String,
      description: String,
      isDone: Boolean
  ): Either[ServiceError.ValidationFailed, Todo] =
    Todo(
      id = 0,
      title = title,
      description = description,
      isDone = isDone
    ).validate
