package edu.yatb


import akka.actor.{ActorSystem, Props}
import akka.io.IO
import com.typesafe.config.ConfigFactory
import edu.yatb.Bots.ScalahelpBot.ScalahelpBot
import org.json4s.jackson.Serialization
import spray.can.Http
import akka.pattern._
import akka.util.Timeout
import spray.http.HttpResponse
import spray.httpx.RequestBuilding._
import scala.annotation.tailrec
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.{Try, Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by alexandr on 1/5/16.
  */
object Boot extends App {

  val bot = new ScalahelpBot()

  bot.init()
}


