package com.galeradev.galeradevblog.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.galeradev.galeradevblog.R
import com.galeradev.galeradevblog.utils.CookieUtil
import kotlinx.android.synthetic.main.activity_settings.*
import java.net.URI

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        logout_button.setOnClickListener {
            val cookies = CookieUtil.cookieManager.cookieStore.get(URI("https://blog.mstefan99.com"))
            for (cookie in cookies) {
                if (cookie.name == "MSTID") {
                    CookieUtil.cookieManager.cookieStore.remove(URI("https://blog.mstefan99.com"), cookie)
                    break
                }
            }
        }
    }
}