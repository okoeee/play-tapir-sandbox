package infrastructure.tables

import domain.model.Todo
import slick.jdbc.MySQLProfile.api.*

class TodoTable(tag: Tag) extends Table[Todo](tag, "todo"):
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def description = column[String]("description")
  def isDone = column[Boolean]("is_done")

  def * = (id, title, description, isDone).mapTo[Todo]
