package edu.yatb.API

import akka.actor.ActorSystem
import akka.io.IO
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import edu.yatb.API.Types._
import edu.yatb.Util.JsonImplicits
import org.json4s.jackson.Serialization
import spray.can.Http
import spray.http._
import spray.httpx._
import spray.util._
import spray.client.pipelining._
import scala.annotation.tailrec
import akka.pattern.ask
import spray.routing.HttpService
import spray.json._
import scala.util.{Try, Failure, Success}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

import JsonImplicits._
import org.json4s.jackson.JsonMethods._
import org.json4s.DefaultFormats
import org.json4s.JsonDSL._
import org.json4s.JsonAST
import spray.routing.SimpleRoutingApp



/**
  * Created by alexandr on 1/5/16.
  */
abstract class TelegramBot(botName: String) extends SimpleRoutingApp {

  protected val _botName: String = botName

  protected val _apiURL = "https://api.telegram.org/bot"
  protected val _fileURL = "https://api.telegram.org/file/bot"


  val botNick = ConfigFactory.load().getString(_botName + ".botNick")

  val apiURL = _apiURL + ConfigFactory.load().getString(_botName + ".botToken")

  val hostURL = ConfigFactory.load().getString(_botName + ".hostURL")

  val hostPort = ConfigFactory.load().getString(_botName + ".hostPort").toInt


  implicit val system : ActorSystem = ActorSystem("telegram-bot")
  import system.dispatcher // execution context for futures

  implicit val formats = DefaultFormats

  val pipeline: HttpRequest => Future[HttpResponse] = sendReceive



  //<editor-fold desc="system methods">


  //
  def init() = {

    //
    val webhookEnabled = ConfigFactory.load().getString(_botName + ".webhookEnabled") == "true"

    //
    launchServer().onComplete {

      //
      case Success(bound) => {

        if (webhookEnabled)
          setWebhook()
        else
          longPolling(apiURL, 0, 100, 20)
      }

      //
      case Failure(ex) => {}
    }
  }


  //send api request to set webhook
  def setWebhook(): Future[HttpResponse] = {

    //
    pipeline(Get(apiURL + "/setWebHook?url=" + hostURL + ":" + hostPort)).mapTo[HttpResponse]
  }


  //start server to listen to webhook updates
  def launchServer(): Future[Http.Bound] = {

    startServer(interface = hostURL, port = hostPort) {

      post {

        path(hostURL) {

          entity(as[Update]) { update =>

            complete(receiveUpdate(update))
          }
        }
      }
    }

      /*
      .onComplete {

      case Success(b) => {

        println(s"Successfully bound to ${b.localAddress}")
      }

      case Failure(ex) => {

        println(ex.getMessage)
        system.shutdown()
      }
    }*/
  }


  //

  @tailrec
  final def longPolling(apiURL: String, updatesOffset: Long, updatesLimit: Long = 1, pollingTimeout: Long = 20): Unit = {
    //import org.json4s._
    //import org.json4s.jackson.JsonMethods._


    //
    implicit val timeout = Timeout(20.seconds)

    //
    val apiURLWithParams = apiURL + "/getUpdates?offset=" + updatesOffset + "&limit=" + updatesLimit + "&timeout=" + pollingTimeout

    //
    var offset: Long = updatesOffset



    println("one more connect!!!" + apiURLWithParams)


    val httpResponseFuture: Future[HttpResponse] = pipeline(Get(apiURLWithParams)).mapTo[HttpResponse]

    val httpResponseTry: Try[HttpResponse] = Try{ Await.result(httpResponseFuture, pollingTimeout.seconds)}

    httpResponseTry match {


      //when we get some response - we process the response and then establish new connection, cos old connection is finished
      case Success(httpResponse) => {

        //run some handler
        //
        val updatesOption = parse(httpResponse.entity.asString).extractOpt[Updates]

        if (updatesOption.isDefined && updatesOption.get.ok) {

          val updates = updatesOption.get.result
          println("!!!Success" + updates)


          updates.map(receiveUpdate(_))

          offset = updates.sortBy(_.update_id).last.update_id + 1
        }
      }


      //when we had no updates in timeout range. We just do nothing and establish new connection - its long polling practice
      case Failure(ex) => {

        //
        println("!!!No data available. Lets reconnect!")
      }
    }

    //make next request for updates
    longPolling(apiURL, offset, updatesLimit, pollingTimeout)
  }


  //</editor-fold desc="system methods">



  //API functions block (sendSMTH) - start

  /***
    *
    * @return
    */
  def getMe : Future[User] = {
    ???
  }


  /***
    *
    * @param method
    * @param params
    * @tparam T
    * @return
    */
  def sendAPICall[T : Manifest](method:String, params:JsonAST.JObject) : Future[T] = {

    implicit val timeout = Timeout(20) //надо чтото сделать с вот этим

    val url = apiURL + "/" + method

    val paramString = compact( render( params ))

    val req = HttpRequest(HttpMethods.POST, Uri(url), entity = HttpEntity(ContentTypes.`application/json`, paramString))

    for(httpResponse <- (IO(Http) ? req).mapTo[HttpResponse]) yield {

      val jsResponse = parse(httpResponse.entity.data.asString)

      val ok = (jsResponse \ "ok").extract[Boolean]

      if(ok) {

        (jsResponse \ "result").extract[T]

      } else {

        throw new Exception // unsuccessful request или типа того

      }
    }
  }


  /***
    *
    * @param chat_id
    * @param text
    * @param parse_mode
    * @param disable_web_page_preview
    * @param reply_to_message_id
    * @param reply_markup
    * @return
    */
  def sendMessage(chat_id: Long,
                  text:String,
                  parse_mode:Option[String] = None,
                  disable_web_page_preview:Option[Boolean] = None,
                  reply_to_message_id:Option[Long] = None,
                  reply_markup:Option[ReplyKeyboardMarkup] = None): Future[Message] = {

    val method = "sendMessage"

    val params =
      ("chat_id" -> chat_id) ~~
        ("text" -> text) ~~
        ("parse_mode" -> parse_mode) ~~
        ("disable_web_page_preview" -> disable_web_page_preview) ~~
        ("reply_to_message_id" -> reply_to_message_id)

    sendAPICall(method, params)
  }

  /***
    *
    * @param chat_id
    * @param from_chat_id
    * @param message_id
    * @return
    */
  def forwardMessage(chat_id: Long,
                     from_chat_id: Long,
                     message_id: Long): Future[Message] = {

    val method = "forwardMessage"

    val params =
      ("chat_id" -> chat_id) ~~
        ("from_chat_id" -> from_chat_id)~~
        ("message_id" -> message_id)

    sendAPICall(method, params)
  }

  /***
    *
    * @param chat_id
    * @param photo
    * @param caption
    * @param reply_to_message_id
    * @param reply_markup
    * @return
    */
  def sendPhoto(chat_id: Long,
               photo:String,
               caption:Option[String] = None,
               reply_to_message_id:Option[Long] = None,
               reply_markup:Option[ReplyKeyboardMarkup] = None
               ) : Future[Message] = {

    val method = "sendPhoto"

    val params =
      ("chat_id" -> chat_id)~~
        ("photo" -> photo)~~
        ("caption" -> caption)~~
        ("reply_to_message_id" -> reply_to_message_id)~~
        ("reply_to_markup" -> reply_markup.toString)

    sendAPICall(method, params)
  }

  /***
    *
    * @param chat_id
    * @param audio
    * @param duration
    * @param performer
    * @param title
    * @param reply_to_message_id
    * @return
    */
  def sendAudio(chat_id: Long,
               audio:String,
               duration:Option[Long] = None,
               performer:Option[String] = None,
               title:Option[String] = None,
               reply_to_message_id:Option[Long] = None) : Future[Message] = {

    val method = "sendAudio"

    val params =
      ("chat_id" -> chat_id)~~
        ("audio" -> audio)~~
        ("duration" -> duration)~~
        ("performer" -> performer)~~
        ("title" -> title)~~
        ("reply_to_message_id" -> reply_to_message_id)

    sendAPICall(method, params)
  }

  /***
    *
    * @param chat_id
    * @param document
    * @param reply_to_message_id
    * @param reply_markup
    * @return
    */
  def sendDocument(chat_id: Long,
                  document:String,
                  reply_to_message_id:Option[Long] = None,
                  reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = {

    val method = "sendDocument"

    val params =
      ("chat_id" -> chat_id)~~
        ("document" -> document)~~
        ("reply_to_message_id" -> reply_to_message_id)~~
        ("reply_markup" -> reply_markup.toString)

    sendAPICall(method, params)
  }

  /***
    *
    * @param chat_id
    * @param sticker
    * @param reply_to_message_id
    * @param reply_markup
    * @return
    */
  def sendSticker(chat_id: Long,
                 sticker:String,
                 reply_to_message_id:Option[Long] = None,
                 reply_markup:Option[ReplyKeyboardMarkup]) : Future[Message] = {

    val method = "sendSticker"

    val params =
      ("chat_id" -> chat_id)~~
        ("sticker" -> sticker)~~
        ("reply_to_message_id" -> reply_to_message_id)~~
        ("reply_markup" -> reply_markup.toString)

    sendAPICall(method, params)
  }

  /***
    *
    * @param chat_id
    * @param video
    * @param duration
    * @param caption
    * @param reply_to_message_id
    * @param reply_markup
    * @return
    */
  def sendVideo(chat_id: Long,
               video:String,
               duration:Option[Long] = None,
               caption:Option[String] = None,
               reply_to_message_id:Option[String] = None,
               reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = {

    val method = "sendVideo"

    val params =
      ("chat_id" -> chat_id)~~
        ("video" -> duration)~~
        ("caption" -> caption)~~
        ("reply_to_message_id" -> reply_to_message_id)~~
        ("reply_mark_up" -> reply_markup.toString)

    sendAPICall(method, params)

  }

  /***
    *
    * @param chat_id
    * @param voice
    * @param duration
    * @param reply_to_message_id
    * @param reply_markup
    * @return
    */
  def sendVoice(chat_id: Long,
               voice:String,
               duration:Option[Long] = None,
               reply_to_message_id:Option[Long] = None,
               reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = {

    val method = "sendVoice"

    val params =
      ("chat_id" -> chat_id)~~
        ("voice" -> voice)~~
        ("duration" -> duration)~~
        ("reply_to_message_id" -> reply_to_message_id)~~
        ("reply_markup" -> reply_markup.toString)

    sendAPICall(method, params)
  }

  /***
    *
    * @param chat_id
    * @param latitude
    * @param longitude
    * @param reply_to_message_id
    * @param reply_markup
    * @return
    */
  def sendLocation(chat_id: Long,
                  latitude:Double,
                  longitude:Double,
                  reply_to_message_id:Option[Long] = None,
                  reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = {

    val method = "sendLocation"

    val params =
        ("chat_id" -> chat_id) ~~
        ("latitude" -> latitude) ~~
        ("longitude" -> longitude) ~~
        ("reply_to_message_id" -> reply_to_message_id) ~~
        ("reply_markup" -> reply_markup.toString)

    sendAPICall(method, params)
  }


  //API functions block (def sendSMTH) - end





  def receiveUpdate(update: Update): Ack = {

    update match {

      //if update is valid with non empty message
      case Update(_, Some(message: Message), _, _) => {

        receiveMessage(message)
        Ack()
      }

      //invalid updat ecase
      case Update(_, None, _, _) => {

        println("we received some shitty update, sorry!")
        Ack(false)
      }
    }
  }



  def receiveMessage(message: Message): Unit = {

    val text = message.text.getOrElse("")

    text match {

      //case of command
      case text if text.startsWith("/") => {

        //
        val command = text.split(' ').head.split('@').head

        command match {

          //
          case "/ping" => {
            //
            sendMessage(chat_id = message.chat.id,
              text = "Pong! Hello, i'm " + botNick + "!",
              parse_mode = None,
              disable_web_page_preview = None,
              reply_to_message_id = Some(message.message_id),
              reply_markup = None)
          }


          //
          case unknownCommand => {

            //
            sendMessage(chat_id = message.chat.id,
              text = "i dont know this command - '" + unknownCommand + "', sorry",
              parse_mode = None,
              disable_web_page_preview = None,
              reply_to_message_id = Some(message.message_id),
              reply_markup = None)
          }
        }
      }


      //
      case _ => {}
    }
  }

}
