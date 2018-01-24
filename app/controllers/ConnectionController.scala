package controllers

import api.ConnectionAPI
import com.google.inject.Inject
import models.{EndpointWeight, WeightRate}
import play.api.mvc.{ControllerComponents, EssentialAction}
import services._
import serialization.ConnectionSerializer._
import serialization.WeightSerializer._

class ConnectionController @Inject() (cc: ControllerComponents,
                                      counter: Counter) extends PlayControllerWrapper(cc) {

  /**
    * Next method returns the next endpoint which will be used.
    *
    * @param id - name of the connection to which the round robin will work on
    * @return   - ConnectionResponse (JSON)
    */
  def next(id: String): EssentialAction = Action {
    apiEncodedResponse(ConnectionAPI.next(id))
  }

  /**
    * Updates the endpoint weight
    *
    * @param endpointName - endpoint to update
    * @return - EndpointWeight after the update
    */
  def update(endpointName: String): EssentialAction = Action { implicit request =>
    val endpointWeight: Either[String, EndpointWeight] = parseJsonField[WeightRate]("weightRate").right.flatMap { weightRate =>
      ConnectionAPI.update(endpointName, weightRate)
    }
    apiEncodedResponse(endpointWeight)
  }

  def getConnection(name: String): EssentialAction = Action {
    apiEncodedResponse(ConnectionAPI.connectionWeight(name))
  }
}
