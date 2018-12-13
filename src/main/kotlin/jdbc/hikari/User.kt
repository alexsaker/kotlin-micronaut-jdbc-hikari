package jdbc.hikari

import com.fasterxml.jackson.annotation.JsonProperty
import com.vladsch.kotlin.jdbc.Row
import java.sql.ResultSet
import java.time.ZonedDateTime
import javax.inject.Singleton

@Singleton
data class User(
        @JsonProperty("id") val id: Int?=0,
        @JsonProperty("name") val name: String,
        @JsonProperty("createdAt") val createdAt: ZonedDateTime? = ZonedDateTime.now(),
        @JsonProperty("updatedAt") val updatedAt: ZonedDateTime? = ZonedDateTime.now()
)

val toUser: (Row) -> User = { row ->
    println(row)
    User(
            row.int("id") ,
            row.string("name"),
            row.zonedDateTime("createdAt"),
            row.zonedDateTime("updatedAt")
    )
}
