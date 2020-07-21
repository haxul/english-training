package com.english.users.request

import javax.validation.constraints.Size

data class UpdateUserRequest(
        @field:Size(min = 8, message = "password is not less than 8 letters")
        val password: String
)