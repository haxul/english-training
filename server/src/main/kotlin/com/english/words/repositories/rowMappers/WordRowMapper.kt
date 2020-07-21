package com.english.words.repositories.rowMappers

import com.english.words.entities.Word
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class WordRowMapper : RowMapper<Word> {
    override fun mapRow(rs: ResultSet, p1: Int): Word {
        val id = rs.getInt("id")
        val value = rs.getString("value")
        val translation = rs.getString("translation")
        return Word(id, value, translation)
    }

}