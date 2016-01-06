package edu.yatb

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import edu.yatb.API.TelegramBot
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
/**
  * Created by alexandr on 1/5/16.
  */
object BotApp extends App {
  implicit val system = ActorSystem("on-spray-can")

  val service = system.actorOf(Props[WebHookServiceActor], "web-hook-service")

  implicit val timeout = Timeout(5.seconds)

  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}
