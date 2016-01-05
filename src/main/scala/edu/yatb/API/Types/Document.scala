package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class Document(file_id:String,
                   thumb:Option[PhotoSize] = None,
                   file_name:Option[String] = None,
                   mime_type:Option[String] = None,
                   file_size:Option[Int] = None)
