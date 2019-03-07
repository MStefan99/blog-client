package com.galeradev.galeradevblog

import android.graphics.Bitmap

class Post(
    var id: Int,
    var title: String,
    var tagline: String,
    var theme_color: String,
    var link: String,
    var author: String,
    var content: String,
    var date: String
) {
    var image: String? = null
    var splash: String? = null
    var imageBitmap: Bitmap? = null
    var splashBitmap: Bitmap? = null
    lateinit var tags: String


}