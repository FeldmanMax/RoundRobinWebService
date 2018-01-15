package serialization

import org.scalatest.FunSuite
import ConnectionSerializer._
import models.ConnectionResponse
import io.circe.syntax._
import io.circe.parser.decode

class ConnectionSerializerSuite extends FunSuite {
  test("ConnectionResponse serialization") {
    val connectionResponse: ConnectionResponse = ConnectionResponse(parentConnectionName = "parentConnection", connectionName = "connection", endpointName = "endpoint", value = "someValue")
    val json: String = connectionResponse.asJson.toString()
    decode[ConnectionResponse](json) match {
      case Left(left) => fail(left)
      case Right(decodedResult) => assert(connectionResponse == decodedResult)
    }
  }
}
