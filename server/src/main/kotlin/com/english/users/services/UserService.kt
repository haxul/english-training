package com.english.users.services

import com.english.users.exceptions.UserExistsException
import com.english.users.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    fun createUser(account:String, password:String ):Int {
        if (userRepository.findUserByAccount(account).isNotEmpty()) throw UserExistsException()
        return userRepository.createUser(account, password)
    }
}