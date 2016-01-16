package dao

import javax.inject._

import org.slf4j.Logger

import scala.util.{Success, Failure}

//import slick.driver.H2Driver
import slick.driver.SQLiteDriver.api._
import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.meta.MTable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by Alexander on 17.12.2015.
  */
//
abstract class BasicDAO[T <: BasicTable[A], A <: BasicTrait] @Inject()(val dbService: DbService)(tableName: String) {

  val tableQuery: TableQuery[T]


  def generateDBTable = {

    //
    dbService.db.run(MTable.getTables(tableName)).map {

      case result => if (result.toList.isEmpty) {

        dbService.db.run(tableQuery.schema.create).onComplete {

          case Success(_) => {}

          case Failure(ex) => println(ex)
        }
      }
    }
  }


  //
  def count(query: Option[Query[T, A, Seq]] = None) = dbService.db.run(query.getOrElse(tableQuery).length.result)


  //
  def list(query: Option[Query[T, A, Seq]])(pageSize: Option[Long])(startFrom: Option[Long]): Future[Seq[A]] = {

    val finalQuery = (pageSize, startFrom) match {

      case (None, None) => query.getOrElse(tableQuery)

      case (Some(pageSize), None) => query.getOrElse(tableQuery).take(pageSize)

      case (None, Some(startFrom)) => query.getOrElse(tableQuery).drop(startFrom)

      case (Some(pageSize), Some(startFrom)) => query.getOrElse(tableQuery).drop(startFrom).take(pageSize)
    }

    //Logger.debug(finalQuery.result.statements.toString())
    dbService.db.run(finalQuery.result)
  }


  //
  def filterQueryById(id: Long): Query[T, A, Seq] =
    tableQuery.filter(_.id === id)


  //
  def find(id: Long): Future[Option[A]] =
    dbService.db.run(filterQueryById(id).result.headOption)


  //
  def insert(entity: A): Future[Long] =
    dbService.db.run(tableQuery returning tableQuery.map(_.id) += entity)


  //
  def update(id: Long, entity: A): Future[A] =
    dbService.db.run(filterQueryById(id).update(entity).map(_ => entity))


  //
  def delete(id: Long): Future[Long] =
    dbService.db.run(filterQueryById(id).delete.map(_.toLong))
  //finally dbService.db.close


  /*
  def syncOp[R](action: slick.dbService.dbio.dbService.dbIOAction[R, slick.dbService.dbio.NoStream, scala.Nothing], duration: Int = 5): R = {

    try Await.result(dbService.db.run(action), Duration(duration, SECONDS))
    //finally dbService.db.close
  }


  def insertSync(entity: A): A = {

    syncOp {
      //tableQuery += entity
      tableQuery returning tableQuery += entity
    }
  }



  //
  def findbService.dbyIdSync(id: Long): Option[A] = {

    syncOp {
      tableQuery.filter(_.id === id).result.headOption
    }
  }



  def findbService.dbySurrogateSync(origin: String, originalCode: String, duration: Int = 5): Option[A] = {

    try Await.result(findbService.dbySurrogate(origin, originalCode), Duration(duration, SECONDS))
    //finally dbService.db.close
  }



  def getIdbService.dbySurrogateSync(origin: String, originalCode: String, duration: Int = 5): Option[Long] = {

    try Await.result(getIdbService.dbySurrogate(origin, originalCode), Duration(duration, SECONDS))
    //finally dbService.db.close
  }

    */
}