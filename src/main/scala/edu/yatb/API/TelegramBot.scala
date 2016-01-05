package edu.yatb.API

import scala.collection.parallel.mutable

/**
  * Created by alexandr on 1/5/16.
  */
class TelegramBot(_token:String) {
  private val token:String = _token
  private val apiURL = "https://api.telegram.org/bot" + token + "/"
  private val fileURL = "https://api.telegram.org/file/bot" + token + "/"

  val webHookURL = ""

  private val actions = ???

  def handle(req:APIRequest) = ???

  def action = ???
}
