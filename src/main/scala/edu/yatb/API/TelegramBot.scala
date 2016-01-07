package edu.yatb.API

import edu.yatb.API.Types.{UserProfilePhotos, ReplyKeyboardMarkup, Message, User}
import spray.http.HttpResponse

import scala.collection.parallel.mutable
import scala.concurrent.Future
import spray.json._
import spray.httpx.RequestBuilding._

/**
  * Created by alexandr on 1/5/16.
  */
trait TelegramBot {

  protected val _token: String

  protected val _apiURL = "https://api.telegram.org/bot"
  protected val _fileURL = "https://api.telegram.org/file/bot"

  protected val _webHookURL: String = ""

  def handle(req:APIRequest)

  /***
    *
    * @return
    */
  def getMe : Future[User] = ???

  /***
    *
    * @param method
    * @param params
    */
  def sendAPICall(method:String, params:JsObject) = {

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
                  reply_markup:Option[ReplyKeyboardMarkup] = None): Future[Message] = ???

  /***
    *
    * @param chat_id
    * @param from_chat_id
    * @param message_id
    * @return
    */
  def forwardMessage(chat_id:Int,
                     from_chat_id:Int,
                     message_id:Int): Future[Message] = ???

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
               ) : Future[Message] = ???

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
               reply_to_message_id:Option[Int] = None) : Future[Message] = ???

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
                  reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = ???

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
                 reply_markup:Option[ReplyKeyboardMarkup]) : Future[Message] = ???

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
               reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = ???

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
               reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = ???

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
                  reply_markup:Option[ReplyKeyboardMarkup] = None) : Future[Message] = ???

  /***
    *
    * @param chat_id
    * @param action
    * @return
    */
  def sendChatAction(chat_id:Int,
                    action:String) = ???

  /***
    *
    * @param user_id
    * @param offset
    * @param limit
    * @return
    */
  def getUserProfilePhotos(user_id:Int,
                          offset:Option[Int] = None,
                          limit:Option[Int] = None) : Future[UserProfilePhotos] = ???
}


trait KukloBot extends TelegramBot {

  val _token: String = ""
}
