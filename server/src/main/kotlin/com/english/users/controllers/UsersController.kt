package com.english.users.controllers

import com.english.users.request.CreateUserRequest
import com.english.users.response.CreateUserResponse
import com.english.users.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UsersController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/register")
    fun getUser(@Valid @RequestBody request: CreateUserRequest): CreateUserResponse {
        val userId = userService.createUser(request.account, request.password)
        return CreateUserResponse(request.account, userId)
    }
}