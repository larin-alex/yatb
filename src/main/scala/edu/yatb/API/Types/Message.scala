package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class Message(message_id:Int,
                  from:Option[User] = None,
                  date:Int,
                  chat:Chat,
                  forward_from:Option[User] = None,
                  forward_date:Option[Int] = None,
                  reply_to_message:Option[Message] = None,
                  text:Option[String] = None,
                  audio:Option[Audio] = None,
                  document:Option[Document] = None,
                  photo:Option[Array[PhotoSize]] = None,
                  sticker:Option[Sticker] = None,
                  video:Option[Video] = None,
                  voice:Option[Voice] = None,
                  caption:Option[String] = None,
                  contact:Option[Contact] = None,
                  location:Option[Location] = None,
                  new_chat_participant:Option[User] = None,
                  left_chat_participant:Option[User] = None,
                  new_chat_title:Option[String] = None,
                  new_chat_photo:Option[Array[PhotoSize]] = None,
                  delete_chat_photo:Option[Boolean] = None,
                  group_chat_created:Option[Boolean] = None,
                  supergroup_chat_created:Option[Boolean] = None,
                  channel_chat_created:Option[Boolean] = None,
                  migrate_to_chat_id:Option[Int] = None,
                  migrate_from_chat_id:Option[Int] = None)

