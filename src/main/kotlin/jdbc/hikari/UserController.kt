package jdbc.hikari

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.reactivex.Single
import javax.inject.Inject

@Controller("/users")
class UserController @Inject  constructor(val userService: UserService){

    @Get("/")
    fun index(): Single<List<User>> {
        return Single.just(userService.getAllUsers())
    }

    @Post("/")
    fun save(@Body user:User): Single<User> {
        return Single.just(userService.saveUser(user))
    }

    @Get("/{id}")
    fun getUserById(id:Int): Single<User> {
        return Single.just(userService.getUserById(id))
    }

    @Delete("/{id}")
    fun deletUserByOd(id:Int): Single<SuccessMessage> {
        userService.deleteUserById(id)
        return Single.just(SuccessMessage(success = true,message = "User deleted successfully"))
    }
}