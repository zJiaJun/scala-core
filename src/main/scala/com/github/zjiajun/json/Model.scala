package com.github.zjiajun.json

import org.json4s.JsonAST.JObject

/**
  * Created by zhujiajun
  * 16/3/11 14:50
  */
case class Model(
  id: String,
  pmp: Option[PMP]


                )

case class PMP(
                var private_auction: Int = 0,
                deals: Option[List[Deal]],
                ext: Option[JObject]
                           )

case class Deal(
                              id: AnyVal,
                              var bidfloor: Int = 0,
                              wseat: Option[List[String]]
                            )
