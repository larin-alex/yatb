package edu.yatb.Util

/**
 * Created by Alexander on 06.01.2016.
 */

import edu.yatb.API.Types._
import spray.httpx.SprayJsonSupport
import spray.json.{RootJsonFormat, DefaultJsonProtocol}


/**
 * Created by alexandr on 1/6/16.
 */
object JsonImplicits extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val ackJsonFormat = jsonFormat1(Ack)

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

  implicit val inlineQueryJsonFormat = jsonFormat4(InlineQuery)

  implicit val messageJsonFormat : RootJsonFormat[Message] = jsonFormat(Message.apply, "message_id", "from", "date",
    "chat", "forward_from", "forward_date", "reply_to_message", "text", "audio", "document", "photo", "sticker",
    "video", "voice", "caption", "contact", "location", "new_chat_participant", "left_chat_participant",
    "new_chat_title", "new_chat_photo", "delete_chat_photo")
    //, "group_chat_created", "supergroup_chat_created", "channel_chat_created", "migrate_to_chat_id", "migrate_from_chat_id"

  implicit val chosenInlineResultJsonFormat = jsonFormat3(ChosenInlineResult)

  implicit val updateJsonFormat = jsonFormat4(Update)
}
//TODO - make smth with message of 26 fields