package com.galeradev.galeradevblog.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.galeradev.galeradevblog.App.Companion.API_URL
import com.galeradev.galeradevblog.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            val queue = Volley.newRequestQueue(this)

            val loginRequest = object : StringRequest(
                Method.POST, "$API_URL/login/", {
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
                    params["login"] = login_input.text.toString()
                    params["current-password"] = password_input.text.toString()
                    return params
                }
            }
            loginRequest.retryPolicy = DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(loginRequest)
        }

        recover_button.setOnClickListener {
            val intent = Intent(this, RecoverCreateActivity::class.java)
            startActivity(intent)
        }
    }
}
