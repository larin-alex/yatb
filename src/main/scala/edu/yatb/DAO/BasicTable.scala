package dao

//import slick.driver.H2Driver.api._
import slick.driver.SQLiteDriver.api._


//val conn = SlickDatabase.forURL("jdbc:h2:mem:test", driver = "org.h2.Driver")
/**
  * Created by Alexander on 17.12.2015.
  */
//
abstract class BasicTable[T](tag: Tag, name: String) extends Table[T](tag, name) {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
}