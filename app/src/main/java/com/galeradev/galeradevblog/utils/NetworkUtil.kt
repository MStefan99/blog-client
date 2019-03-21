package com.galeradev.galeradevblog.utils

import android.app.Activity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.galeradev.galeradevblog.App

object NetworkUtil {
    fun makeRequest(
        activity: Activity,
        method: Int,
        path: String,
        onResponse: (String) -> Unit,
        onError: (VolleyError) -> Unit,
        params: HashMap<String, String>?
    ) {
        val queue = Volley.newRequestQueue(activity)

        val request = object : StringRequest(
            method, "${App.API_URL}/$path/", {
                onResponse(it)
            }, {
                onError(it)
            }
        ) {
            override fun getParams(): Map<String, String>? {
                params?.let {
                    for (param in params) {
                        params[param.key] = param.value
                    }
                    return params
                } ?: run {
                    return null
                }

            }
        }

        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        queue.add(request)
    }

}