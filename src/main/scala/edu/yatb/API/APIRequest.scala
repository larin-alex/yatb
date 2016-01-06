package edu.yatb.API

import spray.httpx.SprayJsonSupport
import spray.json._

/**
  * Created by alexandr on 1/5/16.
  */

case class APIRequest(field1: String, field2: String)

/*
object JsonImplicits extends DefaultJsonProtocol with SprayJsonSupport  {
  //implicit val impAPIRequest: JsonFormat[APIRequest] = jsonFormat2(APIRequest)
  implicit val impAPIRequest = jsonFormat2(APIRequest)
  implicit val format: RootJsonFormat[APIRequest] = jsonFormat2(APIRequest.apply)
}
*/



