package com.galeradev.galeradevblog.utils

import com.galeradev.galeradevblog.App.Companion.COOKIE_NAME
import com.galeradev.galeradevblog.App.Companion.COOKIE_PATH
import java.net.CookieManager
import java.net.URI

object CookieUtil {
    var cookieManager: CookieManager

    init {
        val c = CookieManager()
        cookieManager = c
    }

    fun isLoggedIn(): Boolean {
        val cookies = CookieUtil.cookieManager.cookieStore.get(URI(COOKIE_PATH))
        for (cookie in cookies) {
            if (cookie.name == COOKIE_NAME && cookie.value != "") {
                return true
            }
        }
        return false
    }
}