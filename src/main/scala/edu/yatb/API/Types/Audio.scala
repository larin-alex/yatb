package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class Audio(file_id:String,
                duration:Int,
                performer:Option[String] = None,
                title:Option[String] = None,
                mime_type:Option[String] = None,
                file_size:Option[Int] = None)
