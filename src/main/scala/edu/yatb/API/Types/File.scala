package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class File(file_id:String,
               file_size:Option[Int] = None,
               file_path:Option[String] = None)
