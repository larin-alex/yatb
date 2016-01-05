package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class Sticker(file_id:String,
                  width:Int,
                  height:Int,
                  thumb:Option[PhotoSize] = None,
                  file_size:Option[Int] = None)
