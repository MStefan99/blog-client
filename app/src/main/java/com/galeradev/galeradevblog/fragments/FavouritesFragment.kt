package com.galeradev.galeradevblog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.galeradev.galeradevblog.R
import com.galeradev.galeradevblog.adapters.PostsAdapter
import com.galeradev.galeradevblog.storage.Post
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_posts.*

private const val TAG = "FavouritesFragment"
private const val API_VERSION = "v0.1"
private const val ROUTE = "favourites"
private const val API_URL = "https://blog.mstefan99.com/api/$API_VERSION/$ROUTE/"

class FavouritesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        getPosts()
        return inflater.inflate(R.layout.fragment_posts, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh.setOnRefreshListener {
            getPosts()
        }
    }

    private fun getPosts() {
        val queue = Volley.newRequestQueue(activity)

        val favouritesRequest = object : StringRequest(
            Request.Method.GET, API_URL, {
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

        queue.add(favouritesRequest)

    }
}