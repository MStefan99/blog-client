package com.galeradev.galeradevblog

object Posts {
    val posts: ArrayList<Post>? = null

    fun add(post: Post) {
        posts?.add(post)
    }

    fun get():ArrayList<Post>?{
        return posts
    }
}