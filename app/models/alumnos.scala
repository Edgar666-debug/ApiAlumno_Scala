package models


import play.api.libs.json._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.{ProvenShape, Tag}

case class alumnos(
                   id: Int,
                   nombre: String,
                   apellidos: String,
                   matricula: String
                 )
object alumnos{
  implicit val alumnoFormat: OFormat[alumnos] = Json.format[alumnos]
}

class alumno(tag: Tag) extends Table[alumnos](tag, "alumnos") {

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def nombre: Rep[String] = column[String]("nombre")
  def apellidos: Rep[String] = column[String]("apellidos")
  def matricula: Rep[String] = column[String]("matricula")

  def * : ProvenShape[alumnos] = (id, nombre, apellidos, matricula).mapTo[alumnos]
}