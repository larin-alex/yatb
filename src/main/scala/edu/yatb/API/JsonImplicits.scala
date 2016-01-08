package edu.yatb.API

/**
 * Created by Alexander on 06.01.2016.
 */

import edu.yatb.API.Types._
import org.json4s._
import org.json4s.jackson.JsonMethods._
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol


/**
 * Created by alexandr on 1/6/16.
 */
object JsonImplicits extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val formats = DefaultFormats

  implicit val apiRequestFormats = jsonFormat2(APIRequest)
  //implicit val messageFormats = jsonFormat(Message)

  //implicit val drreads: Reads[Message] = Json.reads[Message]
  //implicit val drwrites: Writes[DatatableResponse[SportCountryLeagueGame]] = Json.writes[DatatableResponse[SportCountryLeagueGame]]
  //implicit val datatableResponseFormat = Json.format[DatatableResponse[SportCountryLeagueGame]]

}