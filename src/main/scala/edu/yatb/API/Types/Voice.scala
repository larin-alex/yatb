package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class Voice(file_id:String,
                duration:Int,
                mime_type:Option[String] = None,
                file_size:Option[Int] = None)
