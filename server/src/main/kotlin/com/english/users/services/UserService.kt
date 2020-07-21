package com.english.users.services

import com.english.users.entities.User
import com.english.users.exceptions.UserExistsException
import com.english.users.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService  {

    @Autowired
    lateinit var userRepository: UserRepository

    fun createUser(account: String, password: String): Int {

        if (userRepository.findUserByAccount(account).isNotEmpty()) throw UserExistsException()
        val encryptedPassword = password
        return userRepository.createUser(account, encryptedPassword)
    }

    fun findUserByAccount(account: String): User? {
        val users: List<User> = userRepository.findUserByAccount(account)
        return if (users.isEmpty()) null else users.first()
    }


    fun findUserByAccountAndPassword(account: String): User? {
        // TODO
        return null
    }
}

