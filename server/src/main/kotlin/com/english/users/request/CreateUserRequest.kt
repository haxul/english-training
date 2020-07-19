package com.english.users.request

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


data class CreateUserRequest(
        @field:NotBlank
        val account: String,

        @field:Size (min = 8, message = "password is not less than 8 letters")
        @field:NotBlank
        val password: String
)