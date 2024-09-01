package domain.service

import domain.model.Todo
import domain.repository.TodoRepository
import domain.shared.ServiceError

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TodoQueryService @Inject() (todoRepository: TodoRepository)(using ExecutionContext):
  def findAll(): Future[Seq[Todo]] = todoRepository.findAll()
  def get(id: Long): Future[Either[ServiceError.NotFound, Todo]] =
    for todo <- todoRepository.get(id)
    yield todo.toRight(ServiceError.NotFound(resource = Todo.getClass.getSimpleName))
