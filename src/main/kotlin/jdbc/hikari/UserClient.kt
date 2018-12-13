package jdbc.hikari

import io.micronaut.http.client.annotation.Client
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus
import io.reactivex.Single

@Client("users")
interface UserClient {

    @Get("/")
    fun index(): Single<List<User>>
}