package com.galeradev.galeradevblog.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.galeradev.galeradevblog.R
import kotlinx.android.synthetic.main.activity_login.*

private const val TAG = "LoginActivity"
private const val API_VERSION = "v0.1"
private const val ROUTE = "login"
private const val API_URL = "https://blog.mstefan99.com/api/$API_VERSION/$ROUTE/"

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            val queue = Volley.newRequestQueue(this)

            val loginRequest = object : StringRequest(
                Method.POST, API_URL, {
                    Toast.makeText(
                        this,
                        it,
                        Toast.LENGTH_LONG
                    ).show()
                    onBackPressed()
                }, {
                    if (it.networkResponse.data != null) {
                        Toast.makeText(
                            this,
                            "Network error ${it.networkResponse.statusCode} ${kotlin.text.String(it.networkResponse.data)}",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
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
