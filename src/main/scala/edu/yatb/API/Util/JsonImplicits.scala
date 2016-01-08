package edu.yatb.API.Util

/**
 * Created by Alexander on 06.01.2016.
 */

import edu.yatb.API.APIRequest
import edu.yatb.API.Types._
import spray.httpx.SprayJsonSupport
import spray.json.{RootJsonFormat, DefaultJsonProtocol}

/**
 * Created by alexandr on 1/6/16.
 */
object JsonImplicits extends DefaultJsonProtocol with SprayJsonSupport{
  implicit val apiRequestFormats = jsonFormat2(APIRequest)

  implicit val audioJsonFormat = jsonFormat6(Audio)

  implicit val chatJsonFormat = jsonFormat6(Chat)

  implicit val contactJsonFormat = jsonFormat4(Contact)

  implicit val documentJsonFormat : RootJsonFormat[Document] = jsonFormat(Document.apply, "chat_id", "thumb", "file_name", "mime_type", "file_size")

  implicit val fileJsonFormat = jsonFormat3(File)

  implicit val locationJsonFormat = jsonFormat2(Location)

  implicit val photoSizeJsonFormat = jsonFormat4(PhotoSize)

  implicit val replyKeyboardMarkupJsonFormat = jsonFormat4(ReplyKeyboardMarkup)

  implicit val stickerJsonFormat = jsonFormat5(Sticker)

  implicit val userJsonFormat = jsonFormat4(User)

  implicit val userProfilePhotosJsonFormat = jsonFormat2(UserProfilePhotos)

  implicit val videoJsonFormat = jsonFormat7(Video)

  implicit val voiceJsonFormat = jsonFormat4(Voice)
}