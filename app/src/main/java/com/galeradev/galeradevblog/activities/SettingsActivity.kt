package com.galeradev.galeradevblog.activities

import android.os.Bundle
import android.widget.Toast
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
            val cookies = CookieUtil.cookieManager.cookieStore.get(URI(COOKIE_PATH))
            for (cookie in cookies) {
                if (cookie.name == COOKIE_NAME) {
                    CookieUtil.cookieManager.cookieStore.remove(URI(COOKIE_PATH), cookie)
                    break
                }
            }
            Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show()
        }
    }
}