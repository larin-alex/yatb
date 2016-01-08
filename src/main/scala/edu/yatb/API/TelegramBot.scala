package edu.yatb.API

import akka.actor.ActorSystem
import akka.io.IO
import akka.util.Timeout
import edu.yatb.API.Types.{Response, ReplyKeyboardMarkup, Message, User}
import edu.yatb.API.Util.JsonImplicits
import spray.can.Http
import spray.http._
import spray.httpx.RequestBuilding._
import akka.pattern.ask

//import spray.json._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import JsonImplicits._
import org.json4s.jackson.JsonMethods._
import org.json4s.DefaultFormats
import org.json4s.JsonDSL._
import org.json4s.JsonAST
/**
  * Created by alexandr on 1/5/16.
  */
trait TelegramBot {

  //protected val token: String

  protected val apiURL = "https://api.telegram.org/bot149980684:AAF-Yy1zUdJZwZwoCJeh9A8Ano5NcFaV-1A"
  protected val fileURL = "https://api.telegram.org/file/bot"

  implicit val system : ActorSystem

  implicit val formats = DefaultFormats

  protected val botURL: String = ""

  //def handle(req:APIRequest)

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
  def sendMessage(chat_id:Int,
                  text:String,
                  parse_mode:Option[String] = None,
                  disable_web_page_preview:Option[Boolean] = None,
                  reply_to_message_id:Option[Int] = None,
                  reply_markup:Option[ReplyKeyboardMarkup] = None): Future[Message] = {

    val method = "sendMessage"

    val params =
      ("chat_id" -> chat_id) ~
        ("text" -> text) ~
        ("parse_mode" -> parse_mode) ~
        ("disable_web_page_preview" -> disable_web_page_preview) ~
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
  def forwardMessage(chat_id:Int,
                     from_chat_id:Int,
                     message_id:Int): Future[Message] = {

    val method = "forwardMessage"

    val params =
      ("chat_id" -> chat_id) ~
        ("from_chat_id" -> from_chat_id)~
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
  def sendPhoto(chat_id:Int,
               photo:String,
               caption:Option[String] = None,
               reply_to_message_id:Option[Int] = None,
               reply_markup:Option[ReplyKeyboardMarkup] = None
               ) : Future[Message] = {

    val method = "sendPhoto"

    val params =
      ("chat_id" -> chat_id)~
        ("photo" -> photo)~
        ("caption" -> caption)~
        ("reply_to_message_id" -> reply_to_message_id)~
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
  def sendAudio(chat_id:Int,
               audio:String,
               duration:Option[Int] = None,
               performer:Option[String] = None,
               title:Option[String] = None,
               reply_to_message_id:Option[Int] = None) : Future[Message] = {

    val method = "sendAudio"

    val params =
      ("chat_id" -> chat_id)~
        ("audio" -> audio)~
        ("duration" -> duration)~
        ("performer" -> performer)~
        ("title" -> title)~
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
  def sendDocument(chat_id:Int,
                  document:String,
                  reply_to_message_id:Option[Int] = None,
                  reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = {

    val method = "sendDocument"

    val params =
      ("chat_id" -> chat_id)~
        ("document" -> document)~
        ("reply_to_message_id" -> reply_to_message_id)~
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
  def sendSticker(chat_id:Int,
                 sticker:String,
                 reply_to_message_id:Option[Int] = None,
                 reply_markup:Option[ReplyKeyboardMarkup]) : Future[Message] = {

    val method = "sendSticker"

    val params =
      ("chat_id" -> chat_id)~
        ("sticker" -> sticker)~
        ("reply_to_message_id" -> reply_to_message_id)~
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
  def sendVideo(chat_id:Int,
               video:String,
               duration:Option[Int] = None,
               caption:Option[String] = None,
               reply_to_message_id:Option[String] = None,
               reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = {

    val method = "sendVideo"

    val params =
      ("chat_id" -> chat_id)~
        ("video" -> duration)~
        ("caption" -> caption)~
        ("reply_to_message_id" -> reply_to_message_id)~
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
  def sendVoice(chat_id:Int,
               voice:String,
               duration:Option[Int] = None,
               reply_to_message_id:Option[Int] = None,
               reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = {

    val method = "sendVoice"

    val params =
      ("chat_id" -> chat_id)~
        ("voice" -> voice)~
        ("duration" -> duration)~
        ("reply_to_message_id" -> reply_to_message_id)~
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
  def sendLocation(chat_id:Int,
                  latitude:Double,
                  longitude:Double,
                  reply_to_message_id:Option[Int] = None,
                  reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = {

    val method = "sendLocation"

    val params =
        ("chat_id" -> chat_id) ~
        ("latitude" -> latitude) ~
        ("longitude" -> longitude) ~
        ("reply_to_message_id" -> reply_to_message_id) ~
        ("reply_markup" -> reply_markup.toString)

    sendAPICall(method, params)
  }

  def processMessage(message: Message): Unit = {

    val text = message.text.get

    message match {

      //case of command
      case msg if msg.text.get.startsWith("/") => {
        //
        msg.text.get match {

          //
          case "/ping" => {
            //
            sendMessage(chat_id = msg.chat.id,
              text = "Pong!",
              parse_mode = None,
              disable_web_page_preview = None,
              reply_to_message_id = Some(msg.message_id),
              reply_markup = None)
          }


          //
          case unknownCommand => {

            //
            sendMessage(chat_id = msg.chat.id,
              text = "i dont know this command - '" + unknownCommand + "', sorry",
              parse_mode = None,
              disable_web_page_preview = None,
              reply_to_message_id = Some(msg.message_id),
              reply_markup = None)
          }
        }
      }


      //
      case _ => {}
    }
  }


}
