package com.english.users.services

import com.english.users.entities.User
import com.english.users.exceptions.UserExistsException
import com.english.users.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User as SpringUser;

@Service
class UserService : UserDetailsService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    fun createUser(account: String, password: String): Int {
        if (userRepository.findUserByAccount(account).isNotEmpty()) throw UserExistsException()
        val encryptedPassword = bCryptPasswordEncoder.encode(password)
        return userRepository.createUser(account, encryptedPassword)
    }

    fun findUserByAccount(account: String): User? {
        val users: List<User> = userRepository.findUserByAccount(account)
        return if (users.isEmpty()) null else users.first()
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = findUserByAccount(username) ?: throw UsernameNotFoundException("user is not found")
        return SpringUser(user.account, user.password, true, true, true, true, emptyList())
    }
}

