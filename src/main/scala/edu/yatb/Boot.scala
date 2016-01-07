package edu.yatb


import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
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
object Boot extends App with ScalaHelpBot {

  implicit val system = ActorSystem("on-spray-can")



  val useWebHook: Boolean = false


  //webhook way of updating
  if (useWebHook) {

    implicit val timeout = Timeout(5.seconds)


    //
    (IO(Http) ? Get(getApiURL + "/setWebHook?url=" + getBotURL)).mapTo[HttpResponse]
      .onComplete {

      //
      case Success(response) => {

        //we have to parse repsonse here and analyse headers (200 is OK, means we set webhook successfully)
        if (true) {
          //
          val service = system.actorOf(Props[WebHookServiceActor], "web-hook-service")

          //
          IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
        }
      }


      //
      case Failure(ex) => {
        print("something went wrong")
      }
    }
  } else { //get updates "manually"

    //
    val startUpdatesOffset = 334905317
    val startUpdatesLimit = 1
    val startPollingTimeout = 20


    //
    longPolling(getApiURL, startUpdatesOffset, startUpdatesLimit, startPollingTimeout)
  }


  @tailrec
  def longPolling(URL: String, updatesOffset: Long, updatesLimit: Long = 1, pollingTimeout: Long = 20): Unit = {

    //
    var entitiesProcessed: Int = 0

    //
    implicit val timeout = Timeout(20.seconds)


    println("one more connect!!!" + URL + "/getUpdates?offset=" + updatesOffset + "&limit=" + updatesLimit + "&timeout=" + pollingTimeout)

    val futureResponse: Future[HttpResponse] = (IO(Http) ? Get(URL + "/getUpdates?offset=" + updatesOffset + "&limit=" + updatesLimit + "&timeout=" + pollingTimeout)).mapTo[HttpResponse]

    val tryResponse: Try[HttpResponse] = Try{ Await.result(futureResponse, pollingTimeout.seconds)}

    tryResponse match {


      //
      case Success(response) => {

        //run some handler
        //
        println("!!!Success" + response.messagePart.toString + response.headers.toString())

        entitiesProcessed += 1
      }


      //
      case Failure(ex) => {

        //
        println("!!!Failure")
      }
    }

    //make next request for updates
    longPolling(URL, updatesOffset + entitiesProcessed)
  }
 }


