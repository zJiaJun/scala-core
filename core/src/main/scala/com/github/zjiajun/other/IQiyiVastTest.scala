package com.github.zjiajun.other

/**
  * Created by zhujiajun
  * 16/2/26 17:16
  */
object IQiyiVastTest extends App {

  val vast = "<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\"?>\\n<VAST xmlns:MM=\\\"http://www.iqiyi.com/cupid\\\" version=\\\"3.0\\\">\\n    <Ad>\\n        <InLine> \\n            <Impression>\\n                <![CDATA[http://imp.beta.madserving.com/imp/v2?bid=105&amp;requestid=2PHUpoicSNc5LmCrCRs2v3hFzKpcecTY1HjSYos&amp;adspaceid=7368C73AD180E73F&amp;cid=343E9885D3691EE6&amp;crea=100131233&amp;adtype=7&amp;pkgname=com.iqiyi&amp;appname=iqiyiVideo&amp;conn=1&amp;carrier=&amp;apitype=9&amp;os=0&amp;osv=&amp;imei=&amp;wma=&amp;aid=&amp;aaid=&amp;idfa=&amp;oid=&amp;uid=&amp;device=&amp;ua=WinHttpClient&amp;ip=10.10.131.166&amp;width=0&amp;height=0&amp;pid=&amp;pcat=&amp;media=&amp;debug=0&amp;density=&amp;lon=&amp;lat=&amp;cell=&amp;mcell=&amp;optiMode=0]]>\\n            </Impression>\\n            <Impression>\\n                <![CDATA[http://114.80.90.115:9999/iqiyi/winnotcie?win={SETTLEMENT}&amp;cid=343E9885D3691EE6&amp;crid=100131233]]>\\n            </Impression>\\n            <Creatives>\\n                <Creative>\\n                    <Linear>\\n                        <VideoClicks>\\n                            <ClickThrough type=\\\"0\\\">\\n                                <![CDATA[ http://clk.beta.madserving.com/clk/v2?bid=105&amp;requestid=2PHUpoicSNc5LmCrCRs2v3hFzKpcecTY1HjSYos&amp;adspaceid=7368C73AD180E73F&amp;cid=343E9885D3691EE6&amp;target=&amp;crea=100131233&amp;adtype=7&amp;pkgname=com.iqiyi&amp;appname=iqiyiVideo&amp;conn=1&amp;carrier=&amp;apitype=9&amp;os=0&amp;osv=&amp;imei=&amp;wma=&amp;aid=&amp;aaid=&amp;idfa=&amp;oid=&amp;uid=&amp;device=&amp;ua=WinHttpClient&amp;ip=10.10.131.166&amp;width=0&amp;height=0&amp;pid=&amp;pcat=&amp;media=&amp;debug=0&amp;density=&amp;lon=&amp;lat=&amp;cell=&amp;mcell=&amp;optiMode=0]]>\\n                            </ClickThrough> \\n                        </VideoClicks>\\n                        <TrackingEvents>\\n                        </TrackingEvents>\\n                        <Icons>\\n                        </Icons>\\n                    </Linear>\\n                </Creative>\\n            </Creatives>\\n        </InLine>\\n    </Ad>\\n</VAST>";
  val impclk = "http://imp.beta.madserving.com/imp/v2?bid=105&amp;requestid=2PHUpoicSNc5LmCrCRs2v3hFzKpcecTY1HjSYos&amp;adspaceid=7368C73AD180E73F&amp;cid=343E9885D3691EE6&amp;crea=100131233&amp;adtype=7&amp;pkgname=com.iqiyi&amp;appname=iqiyiVideo&amp;conn=1&amp;carrier=&amp;apitype=9&amp;os=0&amp;osv=&amp;imei=&amp;wma=&amp;aid=&amp;aaid=&amp;idfa=&amp;oid=&amp;uid=&amp;device=&amp;ua=WinHttpClient&amp;ip=10.10.131.166&amp;width=0&amp;height=0&amp;pid=&amp;pcat=&amp;media=&amp;debug=0&amp;density=&amp;lon=&amp;lat=&amp;cell=&amp;mcell=&amp;optiMode=0"

  val z = impclk.replaceAll("&amp;","&")

  val v = vast.replace("&amp;","&")

  println(z)
  println(v)

}
