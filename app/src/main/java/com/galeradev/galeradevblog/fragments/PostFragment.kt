package com.galeradev.galeradevblog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.galeradev.galeradevblog.R
import com.galeradev.galeradevblog.storage.Post
import kotlinx.android.synthetic.main.fragment_post.*

class PostFragment : Fragment() {
    lateinit var post: Post

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        post_title.text = post.title
        post_tagline.text = post.tagline
        post_date.text = post.date
        post_author.text = post.author
        post_content.text = post.content
        post_tags.text = post.tags
    }
}