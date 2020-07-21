package com.english.users.repositories.rowMapper

import com.english.users.entities.User
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class UserRowMapper : RowMapper<User> {
    override fun mapRow(rs: ResultSet, num: Int): User? {
        val id = rs.getInt("id")
        val account = rs.getString("account")
        val password = rs.getString("password")
        val isDeleted = rs.getBoolean("is_deleted")
        return User(id, account, password, isDeleted)
    }
}