package edu.yatb

import akka.actor.Actor
import edu.yatb.API.{JsonImplicits, KukloBot, TelegramBot, APIRequest}
import spray.routing._
import spray.http._
import spray.json._
import MediaTypes._
/**
  * Created by alexandr on 1/5/16.
  */
class WebHookServiceActor extends Actor with WebHookService {

  def actorRefFactory = context

  def receive = runRoute(route)
}


trait WebHookService extends KukloBot with HttpService {
  import JsonImplicits._
  import spray.httpx.SprayJsonSupport._

  implicit val impAPIRequest: JsonFormat[APIRequest] = jsonFormat1(APIRequest)

  val route = {
    import JsonImplicits._
    import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller
    import spray.httpx.SprayJsonSupport._
    implicit val impAPIRequest: JsonFormat[APIRequest] = jsonFormat1(APIRequest)

    path(getWebHookURL) {
      post {

        entity(as[APIRequest]) { request =>
          handle(request)
          complete("Ok")
        }
      }
    }
  }
}
