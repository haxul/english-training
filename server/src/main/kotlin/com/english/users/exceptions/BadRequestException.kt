package com.english.users.exceptions

import java.lang.RuntimeException

class BadRequestException(private val _message:String) :RuntimeException(_message)