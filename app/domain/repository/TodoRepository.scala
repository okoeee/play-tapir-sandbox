package domain.repository

import domain.model.Todo

class TodoRepository:
  private val todos = Seq(
    Todo(1, "家事", "家の掃除", true),
    Todo(2, "買い物", "食料品の買い出し", false)
  )

  def findAll(): Seq[Todo] = todos
  def get(id: Long): Option[Todo] = todos.find(_.id == id)
