package com.english.users.services

import com.english.users.entities.User
import com.english.users.exceptions.UserExistsException
import com.english.users.exceptions.UserNotFoundException
import com.english.users.repositories.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter


@Service
class UserService {
    val salt: String = "fvau3425,v'zpsfjd.d,aojsdf"

    @Autowired
    private lateinit var environment: Environment

    @Autowired
    private lateinit var userRepository: UserRepository

    fun createUser(account: String, password: String): Int {

        if (userRepository.findUserByAccount(account).isNotEmpty()) throw UserExistsException()
        val bCryptPasswordEncoder = BCryptPasswordEncoder()
        val passSalt = salt + password
        val encryptedPassword = bCryptPasswordEncoder.encode(passSalt)
        return userRepository.createUser(account, encryptedPassword)
    }

    fun findUserByAccount(account: String): User? {
        val users: List<User> = userRepository.findUserByAccount(account)
        return if (users.isEmpty()) null else users.first()
    }

    fun loginUser(account: String, password: String): String {
        val bCryptPasswordEncoder = BCryptPasswordEncoder()
        val rowPassword = salt + password
        val user: User = findUserByAccount(account) ?: throw UserNotFoundException()
        if (user.isDeleted) throw UserNotFoundException()
        val isEqual = bCryptPasswordEncoder.matches(rowPassword, user.password)
        if (!isEqual) throw UserNotFoundException()
        return createJwtToken(user)
    }

    fun createJwtToken(user : User) :String {
        val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256
        val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(environment.getProperty("token.salt"))
        val signingKey: Key = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)
        return Jwts.builder()
                .setSubject(user.id.toString())
                .claim("account", user.account)
                .setExpiration(Date(System.currentTimeMillis() + (environment.getProperty("token.expiration")!!.toLong())))
                .signWith(signatureAlgorithm, signingKey)
                .compact()
    }

    fun updateUserById(password: String, userId: Int) {
        val bCryptPasswordEncoder = BCryptPasswordEncoder()
        val rowPassword = salt + password
        val encryptedPassword = bCryptPasswordEncoder.encode(rowPassword)
        userRepository.updateUserById(encryptedPassword, userId)
    }
}

