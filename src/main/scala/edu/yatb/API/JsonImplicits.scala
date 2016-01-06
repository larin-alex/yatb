package edu.yatb.API

import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol

/**
  * Created by alexandr on 1/6/16.
  */
object JsonImplicits extends DefaultJsonProtocol with SprayJsonSupport{
 implicit val apiRequestFormats = jsonFormat1(APIRequest)
}
