package dao

import javax.inject._

import edu.yatb.Models.{Quote, Setting}
//import slick.driver.H2Driver.api._
import slick.driver.SQLiteDriver.api._

/**
  * Created by Alexander on 13.12.2015.
  */
@Singleton
class QuoteDAO @Inject()(override val dbService: DbService) extends BasicDAO[Quotes, Quote](dbService)("quotes") {

  val tableQuery = TableQuery[Quotes]

  def list2(chat_id: Long, user_id: Option[Long]) = list(Some(tableQuery.filter(x => x.chat_id === chat_id && x.user_id === user_id)))(None)(None)
}


//case with inheritance, we skip 3 first columns definitions here - id, origin and origin_key
class Quotes(tag: Tag) extends BasicTable[Quote](tag, "quotes") {

  def chat_id = column[Long]("chat_id")

  def user_id = column[Option[Long]]("user_id")

  def text = column[String]("text")

  def * = (id.?, chat_id, user_id, text) <> (Quote.tupled, Quote.unapply _)
}
