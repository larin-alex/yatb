package edu.yatb.API

import scala.collection.parallel.mutable

/**
  * Created by alexandr on 1/5/16.
  */
abstract trait TelegramBot {

  protected val _token: String
  private val _apiURL = "https://api.telegram.org/bot"
  private val _fileURL = "https://api.telegram.org/file/bot"
  private val _webHookURL: String = ""


  private val actions = ???

  def handle(req:APIRequest) = ???

  def action = ???


  def getToken = _token
  def getApiURL = _apiURL + _token + "/"
  def getFileURL = _fileURL + _token + "/"
  def getWebHookURL = _webHookURL
}


trait KukloBot extends TelegramBot {

  val _token: String = ""
}
