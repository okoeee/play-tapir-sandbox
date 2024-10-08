package infrastructure.repositoryimpl

import domain.model.Todo
import domain.repository.TodoRepository
import infrastructure.tables.TodoTable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.Future

class TodoRepositoryImpl @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile]
    with TodoRepository:
  import profile.api._

  private val todos = TableQuery[TodoTable]

  def findAll(): Future[Seq[Todo]] =
    db
      .run(todos.result)

  def get(id: Long): Future[Option[Todo]] =
    db
      .run(
        todos
          .filter(_.id === id)
          .result
          .headOption
      )

  def add(todo: Todo): Future[Int] =
    db
      .run(
        todos += todo
      )

  def update(todo: Todo): Future[Int] =
    db
      .run(
        todos
          .filter(_.id === todo.id)
          .update(todo)
      )

  def delete(id: Long): Future[Int] =
    db
      .run(
        todos
          .filter(_.id === id)
          .delete
      )
