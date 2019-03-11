package com.galeradev.galeradevblog.utils

import java.net.CookieManager

object CookieUtil {
    var cookieManager: CookieManager

    init {
        val c = CookieManager()
        cookieManager = c
    }
}