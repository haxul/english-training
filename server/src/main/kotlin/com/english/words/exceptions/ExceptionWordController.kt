package com.english.words.exceptions


import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class ExceptionWordController : ResponseEntityExceptionHandler() {


    @ExceptionHandler(value = [UserHasWordAlready::class])
    fun handleUserHasWordAlready(ex: Exception?, request: WebRequest?): ResponseEntity<Any?>? {
        val errorMessage = ErrorMessage("Error", "User has word already")
        return ResponseEntity(errorMessage, HttpHeaders(), HttpStatus.CONFLICT)
    }

    @ExceptionHandler(value = [WordIsNotFound::class])
    fun handleUserWordIsNotFound(ex: Exception?, request: WebRequest?): ResponseEntity<Any?>? {
        val errorMessage = ErrorMessage("Error", "Word is not found")
        return ResponseEntity(errorMessage, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }
}