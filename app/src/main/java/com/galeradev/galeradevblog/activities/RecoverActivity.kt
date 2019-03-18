package com.galeradev.galeradevblog.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.galeradev.galeradevblog.App
import com.galeradev.galeradevblog.R
import kotlinx.android.synthetic.main.activity_recover.*

class RecoverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover)
        val key = intent?.data.toString().replace("https://blog.mstefan99.com/recover/?key=", "")

        reset_button.setOnClickListener {
            val passwordEmpty = password_input.text.toString().isEmpty()
            val passwordRepeatEmpty = password_repeat_input.text.toString().isEmpty()

            val passwordsMismatch = password_input.text.toString() != password_repeat_input.text.toString()

            if (passwordEmpty || passwordRepeatEmpty) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show()
            } else if (passwordsMismatch) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
            } else {
                val queue = Volley.newRequestQueue(this)

                val recoverRequest = object : StringRequest(
                    Method.PUT, "${App.API_URL}/recover/", {
                        Toast.makeText(
                            this,
                            it,
                            Toast.LENGTH_LONG
                        ).show()
                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
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
                        params["key"] = key
                        params["new-password"] = password_input.text.toString()
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

}