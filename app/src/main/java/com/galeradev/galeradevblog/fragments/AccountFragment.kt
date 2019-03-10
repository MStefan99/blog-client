package com.galeradev.galeradevblog.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.galeradev.galeradevblog.R
import com.galeradev.galeradevblog.storage.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_account.*

private const val TAG = "AccountFragment"
private const val API_VERSION = "v0.1"
private const val ROUTE = "account"
private const val API_URL = "https://blog.mstefan99.com/api/$API_VERSION/$ROUTE/"

class AccountFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val queue = Volley.newRequestQueue(activity)

        val favouritesRequest = object : StringRequest(
            Request.Method.GET, API_URL, {
                val listType = object : TypeToken<User>() {}.type
                val user: User = Gson().fromJson(it, listType)
                tv_username.text = "Hi, ${user.username}!"
                if (user.verified) {
                    tv_email.setTextColor(Color.parseColor("#2bf27f"))
                    tv_email.text = "Your email (${user.email}) is verified!"
                } else {
                    tv_email.setTextColor(Color.parseColor("#f22746"))
                    tv_email.text = "Your email (${user.email}) is not verified!"
                }

            }, {
                if (it.networkResponse.data != null) {
                    Toast.makeText(
                        activity,
                        "Network error ${it.networkResponse.statusCode} ${kotlin.text.String(it.networkResponse.data)}",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        activity,
                        "Network error ${it.networkResponse.statusCode}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }) {}

        queue.add(favouritesRequest)
        return inflater.inflate(R.layout.fragment_account, container, false)
    }
}