package com.english.users.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


data class CreateUserRequest(
        @field:NotBlank(message = "account cannot be empty")
        val account: String,

        @field:Size (min = 8, message = "password is not less than 8 letters")
        @field:NotBlank
        val password: String
)