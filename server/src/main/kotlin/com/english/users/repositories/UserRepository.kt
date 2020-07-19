package com.english.users.repositories

import com.english.users.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement


@Repository
class UserRepository {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    fun createUser(account: String, password: String):Int {
        val sql = "INSERT INTO users (account, password) VALUES (?,?)"
        val holder: KeyHolder = GeneratedKeyHolder()
        val idColumn = "id"
        jdbcTemplate.update({ con ->
            val ps: PreparedStatement = con.prepareStatement(sql, arrayOf(idColumn))
            ps.setString(1, account)
            ps.setString(2, password)
            ps
        }, holder)
        return holder.keys!![idColumn] as Int

    }

    fun findUserByAccount(account: String):List<User> {
        val sql = "SELECT * FROM users WHERE account = ?"
        return jdbcTemplate.query(sql, arrayOf(account)) { rs, _ ->
            val id = rs.getInt("id")
            val account = rs.getString("account")
            val password = rs.getString("password")
            val isDeleted = rs.getBoolean("is_deleted")
            User(id, account, password, isDeleted)
        }
    }
}