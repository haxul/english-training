package com.english.users.controllers

import com.english.users.configurations.SecurityContextHolder
import com.english.users.exceptions.BadRequestException
import com.english.users.request.CreateUserRequest
import com.english.users.request.UpdateUserRequest
import com.english.users.response.CreateUserResponse
import com.english.users.response.LoginResponse
import com.english.users.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UsersController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/signup")
    fun createUser(@Valid @RequestBody request: CreateUserRequest): CreateUserResponse {
        val userId = userService.createUser(request.account, request.password)
        return CreateUserResponse(request.account, userId)
    }

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody request: CreateUserRequest) : LoginResponse {
        val token = userService.loginUser(request.account, request.password)
        return LoginResponse(token, Date())
    }

    @PutMapping
    fun updateUser(@Valid @RequestBody request: UpdateUserRequest): String  {
        val userContext = SecurityContextHolder.loggedUser
        userService.updateUserById(request.password, userContext?.userId ?: throw BadRequestException("User is not found"))
        return "User ${userContext.userId} is updated"
    }
}