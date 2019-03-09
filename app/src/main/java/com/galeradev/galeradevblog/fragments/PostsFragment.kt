package com.galeradev.galeradevblog.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.galeradev.galeradevblog.R
import com.galeradev.galeradevblog.Storage.Post
import com.galeradev.galeradevblog.Storage.Posts
import com.galeradev.galeradevblog.adapters.PostsAdapter
import com.galeradev.galeradevblog.loaders.PostsLoader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.posts_fragment.*

const val TAG = "MainActivity"
const val API_VERSION = "v0.1"
const val URL = "https://blog.mstefan99.com/api/$API_VERSION"

class PostsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val postsLoadObservable =
            PostsLoader.getPostsLoaderObservable("$URL/posts/")

        val postsLoadObserver = postsLoadObservable.subscribe({
            val listType = object : TypeToken<ArrayList<Post>>() {}.type
            val data: ArrayList<Post> = Gson().fromJson(it, listType)
            Log.d("Parsed json:", data.toString())
            Posts.addFromList(data)

            val postsAdapter = PostsAdapter(
                activity!!.applicationContext,
                R.layout.posts_adapter,
                Posts.posts
            )
            posts_list.adapter = postsAdapter

        }, {
            Log.e(TAG, "Network error: $it")
        })
        return inflater.inflate(R.layout.posts_fragment, container, false)
    }
}