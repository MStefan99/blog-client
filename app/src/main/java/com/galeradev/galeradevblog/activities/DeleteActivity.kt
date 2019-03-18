package com.galeradev.galeradevblog.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.galeradev.galeradevblog.App
import com.galeradev.galeradevblog.R
import kotlinx.android.synthetic.main.activity_delete.*

class DeleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        reject_button.setOnClickListener {
            onBackPressed()
        }

        confirm_button.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val registerRequest = object : StringRequest(
                Method.PUT, "${App.API_URL}/delete/", {
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
            ) {}
            registerRequest.retryPolicy = DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(registerRequest)
            onBackPressed()
        }
    }
}