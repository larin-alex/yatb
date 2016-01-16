package edu.yatb.Bots.ScalahelpBot

import javax.inject._

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import dao.{QuoteDAO, DbService}
import edu.yatb.API.TelegramBot
import edu.yatb.API.Types.Message


/**
 * Created by Alexander on 07.01.2016.
 */
@Singleton
class ScalahelpBot @Inject()(override val dbService: DbService,
                             override val quoteDAO: QuoteDAO) extends TelegramBot(dbService, quoteDAO)("ScalahelpBot") {



}
