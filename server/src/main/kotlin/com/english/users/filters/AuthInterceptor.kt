package com.english.users.filters

import com.english.users.configurations.SecurityContextHolder
import com.english.users.dto.SecurityContextUser
import com.english.users.exceptions.AccessForbiddenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.bind.DatatypeConverter


@Component
class AuthInterceptor : HandlerInterceptorAdapter() {

    @Autowired
    private lateinit var environment: Environment

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        try {
            val availableUrl = listOf("/users/login", "/users/signup")
            val tokenHeader = request.getHeader("Authorization")
            val url = request.requestURI
            val method = request.method
            if (method == "POST" && url in availableUrl) return true
            val token = tokenHeader.replace("Bearer ", "")
            val claims: Claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(environment.getProperty("token.salt")))
                    .parseClaimsJws(token).body
            val expiration = claims.expiration
            if (System.currentTimeMillis() > expiration.time) throw AccessForbiddenException()
            val account = claims["account"].toString()
            val userId = claims.subject.toInt()
            SecurityContextHolder.loggedUser = SecurityContextUser(account, userId)
            return true
        } catch (e : Exception) {
            throw AccessForbiddenException()
        }
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        SecurityContextHolder.loggedUser = null
    }
}