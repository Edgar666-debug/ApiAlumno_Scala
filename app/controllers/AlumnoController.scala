package controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import models.alumnosDAO
import models.alumnos
import play.filters.csrf._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

@Singleton
class AlumnoController @Inject()(cc: ControllerComponents, alumnosDAO: alumnosDAO)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  def getAlumnos: Action[AnyContent] = Action.async {
    alumnosDAO.all().map{alumnos =>
      Ok(Json.toJson(alumnos))
    }.recover{
      case ex: Exception => InternalServerError(Json.obj("error" -> "error al obtener los alumnos"))
    }
  }

  def getAlumno(id: Int): Action[AnyContent] = Action.async {
    alumnosDAO.get(id).map{
      case Some(alumno) => Ok(Json.toJson(alumno))
      case None => NotFound(Json.obj("error" -> s"alumno no encontrado con el $id"))
    }.recover{
      case ex: Exception => InternalServerError(Json.obj("error" -> s"error al obtener el producto $id" ))
    }
  }

  def getAlumnoByName(name: String): Action[AnyContent] = Action.async {
    alumnosDAO.getByName(name).map{
      case Some(alumno) => Ok(Json.toJson(alumno))
      case None => NotFound(Json.obj("error" -> s"alumno no encontrado con el $name"))
    }.recover{
      case ex: Exception => InternalServerError(Json.obj("error" -> s"error al obtener el alumno $name" ))
    }
  }

  def insert: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[alumnos].fold(
      errors => Future.successful(BadRequest(JsError.toJson(errors))),
      alumno => {
        alumnosDAO.insert(alumno).map { _ =>
          Created(Json.obj("mensaje" -> "alumno insertado", "alumno" -> Json.toJson(alumno)))
        }
      }
    )
  }


    def delete (id: Int): Action[AnyContent] = Action.async {
    alumnosDAO.delete(id).map { deletedCount =>
      if (deletedCount > 0){
        Ok(Json.obj("status" -> "Alumno eliminado con éxito"))
      } else{
        NotFound
      }
    }.recover( {
      case ex: Exception => InternalServerError(Json.obj("error" -> "error al borrar el alumno"))
    })
  }

  def update(id: Int): Action[JsValue] = Action.async(parse.json) {
    request =>
      request.body.validate[alumnos].fold(
        errors => Future.successful(BadRequest(JsError.toJson(errors))),
        alumno => {
          val updatedCount = alumnosDAO.update(id, alumno)
          updatedCount.map { updatedCount =>
            if (updatedCount > 0) Ok(Json.toJson(alumno))
            else NotFound
          }.recover {
            case ex: Exception => InternalServerError(Json.obj("error" -> "Error al actualizar el alumno"))
          }
        }
      )
  }
}
