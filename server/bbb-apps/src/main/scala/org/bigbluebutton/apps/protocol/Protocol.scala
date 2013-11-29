package org.bigbluebutton.apps.protocol

import spray.json.JsValue
import org.bigbluebutton.apps.models.User
import spray.json.DefaultJsonProtocol
import spray.httpx.SprayJsonSupport

case class Header(event: HeaderEvent, meeting: HeaderMeeting)
case class HeaderEvent(name: String, timestamp: Long, 
                       source: String, reply: Option[ReplyHeader])
case class ReplyHeader(to: String, correlationId: String)                       
case class HeaderMeeting(name: String, externalId: String, sessionId: Option[String] = None)
case class HeaderAndPayload(header: Header, payload: JsValue)

case class MessageProcessException(message: String) extends Exception(message)

object InMessageNameContants {
  val CreateMeetingRequestMessage = "CreateMeetingRequest"
  val RegisterUserRequestMessage = "RegisterUserRequest"
}

case class RegisterUserRequest(header: Header, payload: User) extends InMessage

object HeaderAndPayloadJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {  
  implicit val replyHeaderFormat = jsonFormat2(ReplyHeader)
  implicit val headerEventFormat = jsonFormat4(HeaderEvent)
  implicit val headerMeetingFormat = jsonFormat3(HeaderMeeting)
  implicit val headerFormat = jsonFormat2(Header)  
  implicit val headerAndPayloadFormat = jsonFormat2(HeaderAndPayload)
}