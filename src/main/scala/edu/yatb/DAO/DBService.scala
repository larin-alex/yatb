package dao

import javax.inject._
//import slick.driver.H2Driver.api._
import slick.driver.SQLiteDriver.api._


@Singleton
class DbService {

  //val db = Database.forConfig("h2mem1")
  val db = Database.forURL("jdbc:sqlite:sqlite3.db", driver = "org.sqlite.JDBC")
}