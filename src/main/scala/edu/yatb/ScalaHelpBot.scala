package edu.yatb

import edu.yatb.API.TelegramBot
import com.typesafe.config._


/**
 * Created by Alexander on 07.01.2016.
 */
trait ScalaHelpBot extends TelegramBot {

  protected val _token: String = ConfigFactory.load().getString("ScalaHelpBot.Token")
  protected val _botURL: String = ConfigFactory.load().getString("ScalaHelpBot.BotURL")

}
