package adapter.json.writes

import play.api.libs.json.Format

case class JsValueError(code: String, message: String)
object JsValueError:
  import play.api.libs.json.Json
  given Format[JsValueError] = Json.format[JsValueError]

  def notFound(resource: String): JsValueError = JsValueError("NOT_FOUND", s"$resource not found")
  def validationError(details: String): JsValueError = JsValueError("VALIDATION_ERROR", details)
