package edu.yatb.API.Types

/**
 * Created by alexandr on 1/6/16.
 */
case class Update(update_id:Int,
                   message: Option[Message] = None,
                   inline_query: Option[InlineQuery] = None,
                   choosen_inline_result: Option[ChosenInlineResult] = None)
