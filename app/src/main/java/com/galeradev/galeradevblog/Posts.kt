package com.galeradev.galeradevblog

object Posts {
    val posts = arrayListOf<Post>()

    fun add(post: Post) {
        posts.add(post)
    }

    fun addFromList(list: ArrayList<Post>) {
        for (post: Post in list)
            add(post)
    }
}