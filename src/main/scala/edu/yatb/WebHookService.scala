package edu.yatb

import akka.actor.Actor
import edu.yatb.API.{TelegramBot, APIRequest}
import spray.routing._
import spray.http._
import edu.yatb.API.JsonImplicits._
/**
  * Created by alexandr on 1/5/16.
  */
class WebHookServiceActor(val bot: TelegramBot) extends Actor with WebHookService {
  def actorRefFactory = context

  def receive = runRoute(route)
}

trait WebHookService extends HttpService {

  val bot : TelegramBot

  val route =
    path(bot.webHookURL) {
      post {
        entity(as[APIRequest]) { request =>
          bot.handle(request)
          complete("Ok")
        }
      }
    }

}
