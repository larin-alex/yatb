package edu.yatb

import akka.actor.Actor
import edu.yatb.API.APIRequest
import spray.routing._
import spray.http._
import edu.yatb.API.JsonImplicits._

/**
  * Created by alexandr on 1/5/16.
  */
class WebHookServiceActor extends Actor with WebHookService {

  def actorRefFactory = context

  def receive = runRoute(route)

  //override protected val token: String = _token

  //override def handle(req: APIRequest): Unit = ???
}


trait WebHookService extends HttpService {

  val route = {

    //path(getBotURL) {

      post {

        entity(as[APIRequest]) { request =>
          //handle(request)
          complete("Ok")
        }
      }
    //}
  }
}
