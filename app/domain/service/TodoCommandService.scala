package domain.service

import domain.model.Todo
import domain.repository.TodoRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TodoCommandService @Inject() (
    todoRepository: TodoRepository
)(using ExecutionContext):
  def add(todo: Todo): Future[Int] = todoRepository.add(todo)
  def update(todo: Todo): Future[Int] = todoRepository.update(todo)
  def delete(id: Long): Future[Unit] = todoRepository.delete(id).map(_ => ())
