package com.galeradev.galeradevblog

import android.app.Application

abstract class App : Application() {
    companion object {
        private const val API_VERSION = "v0.1"
        const val COOKIE_PATH = "https://blog.mstefan99.com"
        const val COOKIE_NAME = "MSTID"
        const val API_URL = "https://blog.mstefan99.com/api/$API_VERSION"
    }
}