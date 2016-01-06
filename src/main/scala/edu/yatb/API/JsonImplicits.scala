package edu.yatb.API

/**
 * Created by Alexander on 06.01.2016.
 */
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol

/**
 * Created by alexandr on 1/6/16.
 */
object JsonImplicits extends DefaultJsonProtocol with SprayJsonSupport{
  implicit val apiRequestFormats = jsonFormat2(APIRequest)
}