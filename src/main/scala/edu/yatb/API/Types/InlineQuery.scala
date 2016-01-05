package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class InlineQuery(id:String,
                        from:User,
                        query:String,
                        offset:String)
