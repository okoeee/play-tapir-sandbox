package adapter.json.writes

import play.api.libs.json.{Format, Json}
import sttp.tapir.Schema

sealed abstract class JsValueError(val message: String)

case class JsValueNotFound(override val message: String) extends JsValueError(message)
object JsValueNotFound:
  given Format[JsValueNotFound] = Json.format[JsValueNotFound]

case class JsValueBadRequest(override val message: String) extends JsValueError(message)
object JsValueBadRequest:
  given Format[JsValueBadRequest] = Json.format[JsValueBadRequest]
  given Schema[JsValueBadRequest] = Schema.derived

case class JsValueInternalServerError(override val message: String) extends JsValueError(message)
object JsValueInternalServerError:
  given Format[JsValueInternalServerError] = Json.format[JsValueInternalServerError]
  given Schema[JsValueInternalServerError] = Schema.derived

case class JsValueValidationError(override val message: String) extends JsValueError(message)
object JsValueValidationError:
  given Format[JsValueValidationError] = Json.format[JsValueValidationError]
