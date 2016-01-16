package edu.yatb.Bots.ScalahelpBot

import javax.inject._

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import edu.yatb.API.TelegramBot
import edu.yatb.API.Types.Message


/**
 * Created by Alexander on 07.01.2016.
 */
@Singleton
class ScalahelpBot @Inject()(val db: Int = 1) extends TelegramBot("ScalahelpBot") {


  //implicit val system = ActorSystem("telegram-bot")


  //override def receiveMessage(message: Message) = {}

}
