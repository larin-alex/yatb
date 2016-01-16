package dao

import javax.inject._

import edu.yatb.Models.Setting
//import slick.driver.H2Driver.api._
import slick.driver.SQLiteDriver.api._

/**
  * Created by Alexander on 13.12.2015.
  */
@Singleton
class SettingDAO @Inject()(override val dbService: DbService) extends BasicDAO[Settings, Setting](dbService)("settings") {

  val tableQuery = TableQuery[Settings]

}


//case with inheritance, we skip 3 first columns definitions here - id, origin and origin_key
class Settings(tag: Tag) extends BasicTable[Setting](tag, "settings") {

  def bot_name = column[String]("bot_name")

  def setting_code = column[String]("setting_code")

  def setting_value = column[String]("setting_value")

  def * = (id.?, bot_name, setting_code, setting_value) <> (Setting.tupled, Setting.unapply _)
}
