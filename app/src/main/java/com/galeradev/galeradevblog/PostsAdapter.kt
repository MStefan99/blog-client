package com.galeradev.galeradevblog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PostsAdapter(private val aContext: Context, private val aResource: Int, objects: List<Post>) :
    ArrayAdapter<Post>(aContext, aResource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val title = getItem(position)!!.title
        val tagline = getItem(position)!!.tagline

        val view = LayoutInflater.from(aContext).inflate(aResource, parent, false)

        val tvTitle = view.findViewById<TextView>(R.id.postTitle)
        val tvTagline = view.findViewById<TextView>(R.id.postTagline)

        tvTitle.text = title
        tvTagline.text = tagline

        return view
    }
}
