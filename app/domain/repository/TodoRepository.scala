package domain.repository

import domain.model.Todo

import scala.concurrent.Future

trait TodoRepository:
  def findAll(): Future[Seq[Todo]]
  def get(id: Long): Future[Option[Todo]]
  def add(todo: Todo): Future[Int]
  def update(todo: Todo): Future[Int]
  def delete(id: Long): Future[Int]
