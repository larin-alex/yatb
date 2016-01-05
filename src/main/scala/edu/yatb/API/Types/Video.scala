package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class Video(file_id:String,
                width:Int,
                height:Int,
                duration:Int,
                thumb:Option[PhotoSize] = None,
                mime_type:Option[String] = None,
                file_size:Option[Int] = None)
