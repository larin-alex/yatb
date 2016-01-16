package dao

/**
  * Created by Alexander on 21.12.2015.
  */
//
trait BasicTrait {

  val id: Option[Long]

  def isEqual(entity: BasicTrait): Boolean = false
}