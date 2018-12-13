package jdbc.hikari

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Error
import io.micronaut.http.hateos.JsonError
import io.micronaut.http.hateos.Link

@Error( global = true)
fun error(request: HttpRequest<Any>, e: Throwable): HttpResponse<JsonError> {
    val error = JsonError("Bad Things Happened: ${e.message} !!!!!" )
            .link(Link.SELF, Link.of(request.getUri()))

    return HttpResponse.serverError<JsonError>()
            .body(error)
}