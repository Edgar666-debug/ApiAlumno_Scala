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
  //Metodo para buscar todos los productos
  def all(): Future[Seq[alumnos]] = db.run(students.result)
  //Metodo para buscar un producto por su id
  def get(id: Int): Future[Option[alumnos]] = db.run(students.filter(_.id === id).result.headOption)
  // Metodo para insertar un nuevo producto
  def insert(alumnos: alumnos): Future[Int] = db.run(students += alumnos)
  // Metodo para borrar un producto
  def delete (id: Int): Future[Int] = db.run(students.filter(_.id === id).delete)
  // Metodo para actualizar un producto
  def update(id: Int, alumnos: alumnos): Future[Int] = db.run(students.filter(_.id === id).update(alumnos))

}
