package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class PhotoSize(file_id:String,
                    width:Int,
                    height:Int,
                    file_size:Option[Int] = None)
