package adapter.json.writes

import play.api.libs.json.{Format, Json}

sealed abstract class JsValueError(val message: String)

case class JsValueNotFound(override val message: String) extends JsValueError(message)
object JsValueNotFound:
  given Format[JsValueNotFound] = Json.format[JsValueNotFound]

case class JsValueBadRequest(override val message: String) extends JsValueError(message)
object JsValueBadRequest:
  given Format[JsValueBadRequest] = Json.format[JsValueBadRequest]

case class JsValueValidationError(override val message: String) extends JsValueError(message)
object JsValueValidationError:
  given Format[JsValueValidationError] = Json.format[JsValueValidationError]
