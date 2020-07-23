package com.english.words.controllers

import com.english.users.configurations.SecurityContextHolder
import com.english.users.exceptions.BadRequestException
import com.english.words.CreateWordResponse
import com.english.words.entities.Word
import com.english.words.requests.CreateWordRequest
import com.english.words.requests.TranslateAmazonRequest
import com.english.words.requests.UpdateWordRequest
import com.english.words.response.AmazonTranslationResponse
import com.english.words.services.WordsService
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
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

    @PostMapping("/amazon/translation")
    fun translateWordInAmazon(@RequestBody request: TranslateAmazonRequest) : AmazonTranslationResponse {
        val text = URLEncoder.encode(request.text, "utf-8")
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create("https://71ceo6bjqk.execute-api.us-east-2.amazonaws.com/default/translateRussian/?text=$text"))
                .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return Gson().fromJson(response.body(), AmazonTranslationResponse::class.java)
    }

    @GetMapping
    fun getVocabulary(@RequestParam page:Int) : List<Word> {
        val userId = SecurityContextHolder.loggedUser?.userId ?: throw BadRequestException("User is not found")
        return wordsService.findUserWordsByUserId(userId, page)
    }
}