package com.english.words.requests

import javax.validation.constraints.NotBlank

data class TranslateAmazonRequest(
        @field:NotBlank
        val text: String
)