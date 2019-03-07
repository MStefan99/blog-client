package com.galeradev.galeradevblog

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
    var image: String = "Null"
    var splash: String = "Null"
    lateinit var tags: String


}