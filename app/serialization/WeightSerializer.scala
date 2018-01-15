package serialization

import models.{EndpointWeight, WeightRate}

object WeightSerializer {
  import io.circe._

  implicit val encodeWeightRate: Encoder[WeightRate] = (weightRate: WeightRate) => {
    val members: List[(String, Json)] = List(
      ("isSuccess", Json.fromBoolean(weightRate.isSuccess)),
      ("isPercent", Json.fromBoolean(weightRate.isPercent)),
      ("quantity", Json.fromInt(weightRate.quantity))
    )
    Json.obj(members: _ *)
  }

  implicit val decodeWeightRate: Decoder[WeightRate] = (cursor: HCursor) => {
    for {
      isSuccess <- cursor.downField("isSuccess").as[Boolean]
      isPercent <- cursor.downField("isPercent").as[Boolean]
      quantity <- cursor.downField("quantity").as[Int]
    } yield WeightRate(isSuccess, isPercent, quantity)
  }

  implicit val encodeEndpointWeight: Encoder[EndpointWeight] = (endpointWeight: EndpointWeight) => {
    val members: List[(String, Json)] = List(
      ("name", Json.fromString(endpointWeight.endPointName)),
      ("size", Json.fromInt(endpointWeight.size)))
    Json.obj(members: _ *)
  }
}
