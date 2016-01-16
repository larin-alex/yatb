package edu.yatb.Models

import dao.BasicTrait

/**
  * Created by f0y1e on 16.01.2016.
  */
case class Setting (id: Option[Long],
                    botName: String,
                    settingCode: String,
                    settingValue: String) extends BasicTrait
