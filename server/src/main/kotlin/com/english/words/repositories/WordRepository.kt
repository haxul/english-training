package com.english.words.repositories

import com.english.words.entities.Word
import com.english.words.repositories.rowMappers.WordRowMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement

@Repository
class WordRepository {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    fun addWord(value: String, translation: String): Int {
        val sql = "INSERT INTO words (value, translation) VALUES (?,?)"
        val key: KeyHolder = GeneratedKeyHolder()
        val idColumn = "id"
        jdbcTemplate.update({ con ->
            val ps: PreparedStatement = con.prepareStatement(sql, arrayOf(idColumn))
            ps.setString(1, value)
            ps.setString(2, translation)
            ps
        }, key)
        return key.keys!![idColumn] as Int
    }

    fun saveWordForUser(userId: Int, wordId: Int) {
        val sql = "INSERT INTO words_users VALUES (? , ?)"
        jdbcTemplate.update(sql, userId, wordId)
    }

    fun doesUserHaveWord(userId: Int, wordId: Int): Boolean {
        val sql = "select exists(SELECT * FROM words_users WHERE user_id= ? AND word_id = ?)"
        return jdbcTemplate.queryForObject(sql, arrayOf(userId, wordId)) { rs, _ -> rs.getBoolean("exists") }!!
    }

    fun doesWordExists(value: String): Boolean {
        val sql = "select exists(SELECT * FROM words WHERE value= ?)"
        return jdbcTemplate.queryForObject(sql, arrayOf(value)) { rs, _ -> rs.getBoolean("exists") }!!
    }

    fun findUserWordsByUserId(userId: Int, limit:Int, offset:Int): List<Word> {
        val sql = """
                SELECT words.id, words.value, words.translation FROM words_users aim
                LEFT JOIN words ON aim.word_id = words.id
                WHERE user_id = ? limit ? offset ?
        """.trimIndent()
        return jdbcTemplate.query(sql, arrayOf(userId, limit, offset), WordRowMapper())
    }

    fun findWordByValue(value: String): Word? {
        val sql = "SELECT * FROM words WHERE value =?"
        val words: List<Word> = jdbcTemplate.query(sql, arrayOf(value), WordRowMapper())
        return if (words.isEmpty()) null else words.first()
    }

    fun updateWordValueById(newValue: String, newTranslation: String, wordId: Int) {
        val sql = "UPDATE words SET value =?, translation = ? WHERE id = ?"
        jdbcTemplate.update(sql, newValue, newTranslation, wordId)
    }

    fun findWordById(wordId: Int): Word? {
        val sql = "SELECT * FROM words WHERE id =?"
        val words: List<Word> = jdbcTemplate.query(sql, arrayOf(wordId), WordRowMapper())
        return if (words.isEmpty()) null else words.first()
    }
}