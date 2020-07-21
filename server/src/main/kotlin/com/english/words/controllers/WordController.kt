package com.english.words.controllers

import com.english.users.configurations.SecurityContextHolder
import com.english.users.exceptions.BadRequestException
import com.english.words.CreateWordResponse
import com.english.words.requests.CreateWordRequest
import com.english.words.requests.UpdateWordRequest
import com.english.words.services.WordsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/words")
class WordController {

    @Autowired
    private lateinit var wordsService: WordsService

    @PostMapping
    fun createWord(@Valid @RequestBody request: CreateWordRequest):CreateWordResponse {
        val userId: Int = SecurityContextHolder.loggedUser?.userId ?: throw BadRequestException("User is not found")
        val wordId: Int = wordsService.createWordByUser(userId, request.value, request.translation)
        return CreateWordResponse(wordId)
    }


    @PutMapping
    fun updateWord(@Valid @RequestBody request: UpdateWordRequest):String {
        wordsService.updateWordValueById(request.value, request.translation, request.wordId)
        return "DONE"
    }
}