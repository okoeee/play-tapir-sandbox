package domain.shared

enum ServiceError:
  case NotFound(resource: String) extends ServiceError
  case ValidationFailed(details: String) extends ServiceError
