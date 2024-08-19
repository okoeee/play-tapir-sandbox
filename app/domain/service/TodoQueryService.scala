package domain.service

import domain.model.Todo
import domain.repository.TodoRepository

import javax.inject.Inject

class TodoQueryService @Inject() (todoRepository: TodoRepository):
  def findAll(): Seq[Todo] = todoRepository.findAll()
  def findById(id: Long): Option[Todo] = todoRepository.findById(id)
