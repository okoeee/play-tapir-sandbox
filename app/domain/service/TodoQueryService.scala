package domain.service

import domain.model.Todo
import domain.repository.TodoRepository

import javax.inject.Inject
import scala.concurrent.Future

class TodoQueryService @Inject() (todoRepository: TodoRepository):
  def findAll(): Future[Seq[Todo]] = todoRepository.findAll()
  def get(id: Long): Future[Option[Todo]] = todoRepository.get(id)
