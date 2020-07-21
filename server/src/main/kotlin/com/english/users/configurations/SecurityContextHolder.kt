package com.english.users.configurations

import com.english.users.dto.SecurityContextUser


object SecurityContextHolder {
    private val threadLocalScope: ThreadLocal<SecurityContextUser?> = ThreadLocal()

    var loggedUser: SecurityContextUser?
        get() = threadLocalScope.get()
        set(user) {
            threadLocalScope.set(user)
        }

}