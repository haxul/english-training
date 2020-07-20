package com.english.users.security

import com.english.users.entities.User
import com.english.users.request.LoginRequest
import com.english.users.services.UserService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.bind.DatatypeConverter
import org.springframework.security.core.userdetails.User as SpringUser


class AuthenticationFilter(private val userService: UserService,
                           authenticationManager: AuthenticationManager,
                           private val _environment: Environment) : UsernamePasswordAuthenticationFilter() {

    init {
        super.setAuthenticationManager(authenticationManager)
    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val creds = jacksonObjectMapper().readValue<LoginRequest>(request?.inputStream, LoginRequest::class.java)
        return authenticationManager.authenticate(UsernamePasswordAuthenticationToken(creds.account, creds.password, ArrayList()))
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        val username: String = (authResult!!.principal as SpringUser).username
        val user: User? = userService.findUserByAccount(username)

        val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256
        val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(_environment.getProperty("token.salt"))
        val signingKey: Key = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)
        val token = Jwts.builder()
                .setSubject(user!!.id.toString())
                .setExpiration(Date(System.currentTimeMillis() + _environment.getProperty("token.expiration")!!.toLong()))
                .signWith(signatureAlgorithm, signingKey).compact()
        response!!.addHeader("token", token)
        response.addHeader("userId", java.lang.String.valueOf(user.id))
    }
}