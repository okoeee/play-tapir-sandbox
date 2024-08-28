package adapter.json.writes

case class JsValueError(message: String)
object JsValueError:
  import play.api.libs.json.{Json, Format}
  given Format[JsValueError] = Json.format[JsValueError]

  def notFound(resource: String): JsValueError = JsValueError(s"$resource not found")
  def validationError(details: String): JsValueError = JsValueError(details)
