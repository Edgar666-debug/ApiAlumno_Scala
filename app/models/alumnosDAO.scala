package models


import javax.inject.{Inject, Singleton}
import models.{alumnos, alumno}
import play.api.db.slick.DatabaseConfigProvider
import  slick.jdbc.JdbcProfile
// Sirve para interactuar con la base de datos
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class alumnosDAO @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  // Obtiene el profile de la base de datos para interactuar con ella y sus tabla
  import profile.api._
  private val students = TableQuery[alumno]

  // CRUD
  //Metodo para buscar todos los ALUMNOS
  def all(): Future[Seq[alumnos]] = db.run(students.result)
  //Metodo para buscar un ALUMNOS por su id
  def get(id: Int): Future[Option[alumnos]] = db.run(students.filter(_.id === id).result.headOption)
  //Metodo para buscar un ALUMNOS por su nombre
  def getByName(name: String): Future[Option[alumnos]] = db.run(students.filter(_.nombre === name).result.headOption)
  // Metodo para insertar un nuevo ALUMNOS
  def insert(Alumnos: alumnos): Future[Int] = db.run(students += Alumnos)
  // Metodo para borrar un ALUMNOS
  def delete (id: Int): Future[Int] = db.run(students.filter(_.id === id).delete)
  // Metodo para actualizar un ALUMNOS
  def update(id: Int, alumnos: alumnos): Future[Int] = db.run(students.filter(_.id === id).update(alumnos))

}
