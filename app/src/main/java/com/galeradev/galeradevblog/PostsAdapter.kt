package com.galeradev.galeradevblog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class PostsAdapter(private val aContext: Context, private val aResource: Int, objects: List<Post>) :
    ArrayAdapter<Post>(aContext, aResource, objects) {

    private data class ViewHolder(
        val title: TextView,
        val tagline: TextView,
        val splash: ImageView
    )


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val title = getItem(position)!!.title
        val tagline = getItem(position)!!.tagline
        val splash = getItem(position)!!.image
        val holder: ViewHolder
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(aContext).inflate(aResource, parent, false)
            holder = ViewHolder(
                view.findViewById(R.id.postTitle),
                view.findViewById(R.id.postTagline),
                view.findViewById(R.id.postImage)
            )
            view.tag = holder

        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }


        holder.title.text = title
        holder.tagline.text = tagline
        //TODO: Display image

        return view
    }
}