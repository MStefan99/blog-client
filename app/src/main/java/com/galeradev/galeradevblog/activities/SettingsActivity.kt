package com.galeradev.galeradevblog.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
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
            login_button.visibility = View.VISIBLE
            register_button.visibility = View.VISIBLE
            logout_button.visibility = View.GONE
        }

        login_button.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.recreate()
        }

        register_button.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        if (CookieUtil.logged_in()) {
            login_button.visibility = View.GONE
            register_button.visibility = View.GONE
            logout_button.visibility = View.VISIBLE
        } else {
            login_button.visibility = View.VISIBLE
            register_button.visibility = View.VISIBLE
            logout_button.visibility = View.GONE

        }
    }
}