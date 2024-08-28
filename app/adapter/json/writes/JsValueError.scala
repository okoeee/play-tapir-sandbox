package adapter.json.writes

case class JsValueError(code: String, message: String)
object JsValueError:
  import play.api.libs.json.{Json, Format}
  given Format[JsValueError] = Json.format[JsValueError]

  def notFound(code: String, resource: String): JsValueError = JsValueError(code, s"$resource not found")
  def validationError(code: String, details: String): JsValueError = JsValueError(code, details)
