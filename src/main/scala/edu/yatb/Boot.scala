package edu.yatb


import akka.actor.{ActorSystem, Props}
import akka.io.IO
import com.typesafe.config.ConfigFactory
import org.json4s.jackson.Serialization
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
object Boot extends App /*with ScalaHelpBot*/ {

  implicit val system = ActorSystem("on-spray-can")

  val apiURL = ConfigFactory.load().getString("TelegramBot.ApiURL") + ConfigFactory.load().getString("ScalaHelpBot.Token")
  val botURL = ConfigFactory.load().getString("ScalaHelpBot.BotURL")


  val useWebHook: Boolean = false


  //webhook way of updating
  if (useWebHook) {

    implicit val timeout = Timeout(5.seconds)


    //
    (IO(Http) ? Get(apiURL + "/setWebHook?url=" + botURL)).mapTo[HttpResponse]
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
    val startUpdatesOffset = ConfigFactory.load().getLong("ScalaHelpBot.UpdatesOffset")
    val startUpdatesLimit = ConfigFactory.load().getLong("ScalaHelpBot.UpdatesLimit")
    val startPollingTimeout = ConfigFactory.load().getLong("ScalaHelpBot.PollingTimeout")


    //
    longPolling(apiURL, startUpdatesOffset, startUpdatesLimit, startPollingTimeout)
  }


  @tailrec
  def longPolling(apiURL: String, updatesOffset: Long, updatesLimit: Long = 1, pollingTimeout: Long = 20): Unit = {
    import org.json4s._
    import org.json4s.jackson.JsonMethods._
    import edu.yatb.API.Types._

    //
    //implicit val formats = DefaultFormats
    implicit val formats = {
      Serialization.formats(FullTypeHints(List(classOf[Message])))
    }

    //
    implicit val timeout = Timeout(20.seconds)

    //
    val apiURLWithParams = apiURL + "/getUpdates?offset=" + updatesOffset + "&limit=" + updatesLimit + "&timeout=" + pollingTimeout

    //
    var entitiesProcessed: Int = 0



    println("one more connect!!!" + apiURLWithParams)


    val futureResponse: Future[HttpResponse] = (IO(Http) ? Get(apiURLWithParams)).mapTo[HttpResponse]

    val tryResponse: Try[HttpResponse] = Try{ Await.result(futureResponse, pollingTimeout.seconds)}

    tryResponse match {


      //when we get some response - we process the response and then establish new connection, cos old connection is finished
      case Success(response) => {

        //run some handler
        //
        val optionResponse = parse(response.entity.asString).extractOpt[Response]

        if (optionResponse.isDefined && optionResponse.get.ok) {

          println("!!!Success" + response.entity.asString)
          println(optionResponse.get.toString)

          var responseStr = response.entity.asString.split("\\[", 2).last //remove left part with "["
          responseStr = responseStr.split("\\]").dropRight(1).mkString //remove right part after "]"
          responseStr = responseStr.split(",", 2).last.dropRight(1) //remove right "}"
          responseStr = responseStr.split(":", 2).last

          println(responseStr)

          val parsedMessage = parse(responseStr).extract[Message]
          println(parsedMessage)
        }



        entitiesProcessed += 1
      }


      //when we had no updates in timeout range. We just do nothing and establish new connection - its long polling practice
      case Failure(ex) => {

        //
        println("!!!No data available. Lets reconnect!")
      }
    }

    //make next request for updates
    longPolling(apiURL, updatesOffset + entitiesProcessed, updatesLimit, pollingTimeout)
  }
 }


