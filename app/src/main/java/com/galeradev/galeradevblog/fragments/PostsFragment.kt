package com.galeradev.galeradevblog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.galeradev.galeradevblog.App.Companion.API_URL
import com.galeradev.galeradevblog.R
import com.galeradev.galeradevblog.adapters.PostsAdapter
import com.galeradev.galeradevblog.storage.Post
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        getPosts()
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onResume() {
        super.onResume()
        swipe_refresh.setOnRefreshListener {
            getPosts()
        }
    }

    private fun getPosts() {
        val queue = Volley.newRequestQueue(activity)

        val postsRequest = object : StringRequest(
            Method.GET, "$API_URL/posts/", {
                val listType = object : TypeToken<ArrayList<Post>>() {}.type
                val posts: ArrayList<Post> = Gson().fromJson(it, listType)

                val postsAdapter = PostsAdapter(
                    activity!!.applicationContext,
                    R.layout.posts_adapter,
                    posts
                )
                posts_list.adapter = postsAdapter
                swipe_refresh.isRefreshing = false
            }, { error ->
                error.networkResponse?.let {
                    Toast.makeText(
                        activity,
                        "Network error ${error.networkResponse.statusCode} ${kotlin.text.String(error.networkResponse.data)}",
                        Toast.LENGTH_LONG
                    ).show()
                } ?: run {
                    Toast.makeText(
                        activity,
                        "Network error",
                        Toast.LENGTH_LONG
                    ).show()
                }
                swipe_refresh.isRefreshing = false
            }) {}

        queue.add(postsRequest)

    }
}