package com.galeradev.galeradevblog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.galeradev.galeradevblog.R
import com.galeradev.galeradevblog.Storage.Post
import com.galeradev.galeradevblog.Storage.Posts
import com.galeradev.galeradevblog.adapters.PostsAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_posts.*

private const val TAG = "PostsFragment"
private const val API_VERSION = "v0.1"
private const val ROUTE = "posts"
private const val API_URL = "https://blog.mstefan99.com/api/$API_VERSION/$ROUTE/"

class PostsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val queue = Volley.newRequestQueue(activity)

        val stringRequest = StringRequest(
            Request.Method.GET, API_URL,
            Response.Listener<String> {
                // Display the first 500 characters of the response string.
                val listType = object : TypeToken<ArrayList<Post>>() {}.type
                val data: ArrayList<Post> = Gson().fromJson(it, listType)
                Posts.addFromList(data)

                val postsAdapter = PostsAdapter(
                    activity!!.applicationContext,
                    R.layout.posts_adapter,
                    Posts.posts
                )
                posts_list.adapter = postsAdapter
            },
            Response.ErrorListener {
                Toast.makeText(
                    activity,
                    "Network error ${it.networkResponse.statusCode}",
                    Toast.LENGTH_LONG
                ).show()
            })

        queue.add(stringRequest)
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }
}