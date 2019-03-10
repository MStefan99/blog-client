package com.galeradev.galeradevblog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.galeradev.galeradevblog.R
import kotlinx.android.synthetic.main.fragment_login.*


private const val TAG = "LoginFragment"
private const val API_VERSION = "v0.1"
private const val ROUTE = "login"
private const val API_URL = "https://blog.mstefan99.com/api/$API_VERSION/$ROUTE/"

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button.setOnClickListener {
            val queue = Volley.newRequestQueue(activity)

            val loginRequest = object : StringRequest(
                Method.POST, API_URL, {
                    Toast.makeText(
                        activity,
                        it,
                        Toast.LENGTH_LONG
                    ).show()
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
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["login"] = login_field.text.toString()
                    params["current-password"] = password_field.text.toString()
                    return params
                }
            }
            queue.add(loginRequest)
        }
    }
}
