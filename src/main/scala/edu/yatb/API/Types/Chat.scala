package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class Chat(id:Int,
                `type`:String, //по идее должен быть type а не chat_type но слово type зарезервировано
                title:Option[String] = None,
                username:Option[String] = None,
                first_name:Option[String] = None,
                last_name:Option[String] = None)
