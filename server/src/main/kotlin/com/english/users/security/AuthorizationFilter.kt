package com.english.users.security

import io.jsonwebtoken.Jwts
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.bind.DatatypeConverter


class AuthorizationFilter(authenticationManager: AuthenticationManager?, private val _environment: Environment) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization") ?: return
        if (!authorizationHeader.startsWith("Bearer")) {
            chain.doFilter(request, response)
            return
        }
        val authentication = getAuthentication(request)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        return try {
            val authorizationHeader = request.getHeader("Authorization") ?: return null
            val token = authorizationHeader.replace("Bearer ", "")
            val claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(_environment.getProperty("token.salt")))
                    .parseClaimsJws(token).body
            val userId = claims.subject ?: return null
            UsernamePasswordAuthenticationToken(Integer.valueOf(userId), null, emptyList())
        } catch (e: Exception) {
            null
        }
    }

}