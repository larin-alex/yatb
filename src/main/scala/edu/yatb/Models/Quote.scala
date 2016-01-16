package edu.yatb.Models

import dao.BasicTrait

/**
  * Created by f0y1e on 16.01.2016.
  */
case class Quote (id: Option[Long],
                  chat_id: Long,
                  user_id: Option[Long],
                  text: String) extends BasicTrait
