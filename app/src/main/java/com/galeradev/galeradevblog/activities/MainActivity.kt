package com.galeradev.galeradevblog.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.galeradev.galeradevblog.R
import com.galeradev.galeradevblog.exceptions.NoSuchCookieException
import com.galeradev.galeradevblog.fragments.AccountFragment
import com.galeradev.galeradevblog.fragments.FavouritesFragment
import com.galeradev.galeradevblog.fragments.LoginFragment
import com.galeradev.galeradevblog.fragments.PostsFragment
import com.galeradev.galeradevblog.utils.CookieUtil
import com.galeradev.galeradevblog.utils.SharedPrefsUtil
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.net.CookieHandler
import java.net.HttpCookie
import java.net.URI

const val COOKIE_PATH = "https://blog.mstefan99.com"
const val COOKIE_NAME = "MSTID"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CookieHandler.setDefault(CookieUtil.cookieManager)
        val sharedPrefsUtil = SharedPrefsUtil(this)

        try {
            val cookieValue = sharedPrefsUtil.loadCookie(COOKIE_NAME)

            val cookie = HttpCookie(COOKIE_NAME, cookieValue)
            if (cookie.value != "") {
                CookieUtil.cookieManager.cookieStore.add(URI(COOKIE_PATH), cookie)
            }
        } catch (e: NoSuchCookieException) {
            Log.d("MainActivity", e.toString())
        }

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, PostsFragment())
            .commit()
        nav_view.setCheckedItem(R.id.nav_posts)


        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_posts -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, PostsFragment())
                    .commit()
            }
            R.id.nav_favourites -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, FavouritesFragment())
                    .commit()
            }
            R.id.nav_account -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, AccountFragment())
                    .commit()
            }
            R.id.nav_login -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, LoginFragment())
                    .commit()
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPause() {
        super.onPause()
        val sharedPrefsUtil = SharedPrefsUtil(this)
        val cookies = CookieUtil.cookieManager.cookieStore.get(URI(COOKIE_PATH))
        var value = ""
        for (cookie in cookies) {
            if (cookie.name == COOKIE_NAME && cookie.value != "") {
                value = cookie.value
                break
            }
        }
        sharedPrefsUtil.saveCookie(COOKIE_NAME, value)
    }
}
