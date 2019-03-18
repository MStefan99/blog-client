package com.galeradev.galeradevblog.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.galeradev.galeradevblog.App
import com.galeradev.galeradevblog.R
import kotlinx.android.synthetic.main.activity_create_recover.*

class RecoverCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recover)

        reset_button.setOnClickListener {
            val queue = Volley.newRequestQueue(this)

            val recoverRequest = object : StringRequest(
                Method.POST, "${App.API_URL}/recover_create/", {
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
                    return params
                }
            }

            recoverRequest.retryPolicy = DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(recoverRequest)
        }
    }
}