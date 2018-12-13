package jdbc.hikari

import java.lang.*
import io.micronaut.http.server.exceptions.*
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.hateos.JsonError
import javax.inject.Singleton


@Singleton
class MyExceptionHandler : ExceptionHandler<RuntimeException,MutableHttpResponse<JsonError>> {
    override fun handle(request: HttpRequest<*>?, exception: RuntimeException?): MutableHttpResponse<JsonError> {
        return HttpResponse.serverError<JsonError>()
                .body(JsonError("An internal runtime error occured (Details: ${exception.toString()})"))
    }
}