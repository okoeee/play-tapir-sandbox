package domain.service

import domain.model.Todo
import domain.repository.TodoRepository

import javax.inject.Inject
import scala.concurrent.Future

class TodoCommandService @Inject() (todoRepository: TodoRepository):
  def add(todo: Todo): Future[Int] = todoRepository.add(todo)
  def update(todo: Todo): Future[Int] = todoRepository.update(todo)
  def delete(todo: Todo): Future[Int] = todoRepository.delete(todo.id)
