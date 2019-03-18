package com.galeradev.galeradevblog.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.galeradev.galeradevblog.App.Companion.COOKIE_NAME
import com.galeradev.galeradevblog.App.Companion.COOKIE_PATH
import com.galeradev.galeradevblog.R
import com.galeradev.galeradevblog.utils.CookieUtil
import com.galeradev.galeradevblog.utils.SharedPrefsUtil
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
                    SharedPrefsUtil(this).removeCookie(COOKIE_NAME)
                    break
                }
            }
            setButtonVisibility("IRdo")
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

        delete_button.setOnClickListener {
            intent = Intent(this, DeleteActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        if (CookieUtil.isLoggedIn()) {
            setButtonVisibility("irDO")
        } else {
            setButtonVisibility("IRdo")
        }
    }

    private fun setButtonVisibility(buttons: String) {
        buttons.forEach { button ->
            getButton(button)?.visibility = if (button.isUpperCase()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun getButton(button: Char): Button? {
        return when (button) {
            'I', 'i' -> login_button
            'R', 'r' -> register_button

            'D', 'd' -> delete_button
            'O', 'o' -> logout_button
            else -> null
        }
    }
}