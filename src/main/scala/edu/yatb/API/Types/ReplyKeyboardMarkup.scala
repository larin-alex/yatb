package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class ReplyKeyboardMarkup(keyboard:Array[Array[String]],
                              resize_keyboard:Option[Boolean] = None,
                              one_time_keyboard:Option[Boolean] = None,
                              selective:Option[Boolean] = None)
