package com.english.words.services

import com.english.words.entities.Word
import com.english.words.exceptions.UserHasWordAlready
import com.english.words.exceptions.WordIsNotFound
import com.english.words.repositories.WordRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WordsService {

    @Autowired
    private lateinit var wordRepository: WordRepository

    @Transactional
    fun createWordByUser(userId: Int, value: String, translation: String): Int {
        val word: Word? = wordRepository.findWordByValue(value)
        if (word == null) {
            val wordId: Int = wordRepository.addWord(value, translation)
            wordRepository.saveWordForUser(userId, wordId)
            return wordId
        }
        if (wordRepository.doesUserHaveWord(userId, word.id)) throw UserHasWordAlready()
        wordRepository.saveWordForUser(userId, word.id)
        return word.id
    }


    fun updateWordValueById(newValue: String, newTranslation: String, wordId: Int) {
        if (wordRepository.findWordById(wordId) == null) throw WordIsNotFound()
        wordRepository.updateWordValueById(newValue, newTranslation, wordId)
    }

}