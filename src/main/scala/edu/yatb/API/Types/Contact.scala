package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class Contact(phone_number:String,
                  first_name:String,
                  last_name:Option[String],
                  user_id:Option[Int] = None)
