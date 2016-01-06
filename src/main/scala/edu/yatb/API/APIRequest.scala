package edu.yatb.API

import spray.httpx.SprayJsonSupport
import spray.json._

/**
  * Created by alexandr on 1/5/16.
  */

case class APIRequest(field1: String)

object JsonImplicits extends DefaultJsonProtocol {
  implicit val impAPIRequest: JsonFormat[APIRequest] = jsonFormat1(APIRequest)
}




