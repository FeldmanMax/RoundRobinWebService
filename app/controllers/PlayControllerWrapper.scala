package controllers

import io.circe.{Decoder, Encoder, Json}
import play.api.libs.json.JsValue
import play.api.mvc._
import io.circe.parser.decode
import io.circe.syntax._

abstract class PlayControllerWrapper(val cc: ControllerComponents) extends AbstractController(cc) {

  def apiEncodedResponse[A](data: Either[String, A])(implicit m: Encoder[A]): Result = {
    data match {
      case Left(error) => Ok(Map("success" -> "false", "error" -> error).asJson.toString()).withHeaders(CONTENT_TYPE -> "application/json")
      case Right(info) => Ok(Map("success" -> Json.fromString("true"), "data" -> info.asJson).asJson.toString()).withHeaders(CONTENT_TYPE -> "application/json")
    }
  }

  def parseJsonRequest[A: Decoder](implicit request: Request[AnyContent]): Either[String, A] = {
    parse(request, jsBody => jsBody)
  }

  def parseJsonField[A: Decoder](field: String)(implicit request: Request[AnyContent]): Either[String, A] = {
    parse(request, jsBody => (jsBody \ field).get)
  }

  private def parse[A: Decoder](request: Request[AnyContent], extract: JsValue => JsValue): Either[String, A] = {
    request.body.asJson match {
      case None => Left(s"Content is not JSON")
      case Some(jsBody) => decode[A](extract(jsBody).toString()).left.map(x=>x.toString)
    }
  }
}
