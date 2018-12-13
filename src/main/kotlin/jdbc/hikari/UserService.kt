package jdbc.hikari



import com.vladsch.kotlin.jdbc.session
import javax.inject.Inject
import javax.inject.Singleton
import com.vladsch.kotlin.jdbc.sqlQuery
import org.slf4j.LoggerFactory
import javax.sql.DataSource

@Singleton
//class UserService @Inject constructor(
//        @Value("\${datasources.default.url}") val url:String,
//        @Value("\${datasources.default.username}") val username:String,
//        @Value("\${datasources.default.password}") val password:String
//
//)
class UserService @Inject constructor(
        val dataSource: DataSource

) {
    private val logger = LoggerFactory.getLogger(UserService::class.java)
    //   private  val session by lazy { session(url = url,user=username,password = password) }
    private val session by lazy { session(dataSource) }
init {
    try {
        session(dataSource).execute(sqlQuery("""
CREATE TABLE  IF NOT EXISTS users (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(64) DEFAULT NULL,
  createdAt timestamp NOT NULL,
  updatedAt timestamp NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id (id)
) ENGINE=InnoDB
    """.trimIndent()))
    }catch (e:Exception){
       logger.error(e.message)
    }

}

    fun getAllUsers(): List<User> {
        val allUsersQuery = sqlQuery("select * from users")
        return session.list(allUsersQuery) { row -> toUser(row) }
    }

    fun saveUser(user:User):User? {
        val updatedUserId = if (user.id != null) {
            session.updateGetId( sqlQuery("""
INSERT INTO users (name)
VALUES (:name)
        """.trimIndent(), mapOf("name" to user.name))
           )
        } else {
            sqlQuery("""
UPDATE users SET name=:name,updatedAt=NOW() WHERE id=:id
        """.trimIndent(), mapOf("id" to user.id, "name" to user.name))
            user.id
        }
      return getUserById(updatedUserId)
    }

    fun getUserById(id:Int?=0):User?{
        val query = sqlQuery("select * from users where id = ?", id)
        return session.first(query, toUser)
    }

    fun deleteUserById(id:Int?=0):Int?{
        val query = sqlQuery("DELETE FROM users where id = ?", id)
        return session.updateGetId(query)
    }
}

