package domain.shared

enum ServiceError(val code: String):
  case NotFound(resource: String) extends ServiceError(code = "NOT_FOUND")
  case ValidationFailed(details: String) extends ServiceError(code = "VALIDATION_FAILED")
