package adapter.json.writes

case class JsValueError(code: String, message: String)
object JsValueError:
  import play.api.libs.json.{Json, Writes}
  given Writes[JsValueError] = Json.writes[JsValueError]

  def notFound(resource: String): JsValueError = JsValueError("NOT_FOUND", s"$resource not found")
  def validationError(details: String): JsValueError = JsValueError("VALIDATION_ERROR", details)
