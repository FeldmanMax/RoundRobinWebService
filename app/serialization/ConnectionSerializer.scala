package serialization

import models.{ConnectionResponse, ConnectionWeight}
import WeightSerializer.encodeEndpointWeight

object ConnectionSerializer {
  import io.circe._
  import io.circe.syntax._

  implicit val encodeConnectionResponse: Encoder[ConnectionResponse] = (a: ConnectionResponse) => {
    val members = List[(String, Json)](
      ("parentConnection", Json.fromString(a.parentConnectionName)),
      ("connection", Json.fromString(a.connectionName)),
      ("endpoint", Json.fromString(a.endpointName)),
      ("value", Json.fromString(a.value))
    )
    Json.obj(members: _ *)
  }

  implicit val decodeConnectionResponse: Decoder[ConnectionResponse] = (c: HCursor) => {
    for {
      parentConnectionName <- c.downField("parentConnection").as[String]
      connectionName <- c.downField("connection").as[String]
      endpointName <- c.downField("endpoint").as[String]
      value <- c.downField("value").as[String]
    } yield ConnectionResponse(parentConnectionName, connectionName, endpointName, value)
  }

  implicit val encodeConnectionWeight: Encoder[ConnectionWeight] = (weight: ConnectionWeight) => {
    val members: List[(String, Json)] = List(
      ("totalWeight", Json.fromInt(weight.totalWeight)),
      ("endpointsWeight", Json.fromValues(weight.endpointToMap.map(x=>x._2.asJson).toList))
    )
    Json.obj(members: _ *)
  }
}
