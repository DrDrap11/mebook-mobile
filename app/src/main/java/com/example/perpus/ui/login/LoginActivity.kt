package com.example.perpus.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.perpus.R
import com.example.perpus.ui.login.LoginFragment

class LoginActivity : AppCompatActivity() {

    private var lastPressedTime: Long = 0
    private val PERIOD = 2000

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login: Fragment = LoginFragment()
        moveToFragment(login)
    }

    private fun moveToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, fragment.javaClass.simpleName).addToBackStack(null)
            .commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            when (event.action) {
                KeyEvent.ACTION_DOWN -> {
                    if (event.downTime - lastPressedTime < PERIOD) {
                        val baIntent = Intent(Intent.ACTION_MAIN)
                        baIntent.addCategory(Intent.CATEGORY_HOME)
                        baIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(baIntent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Tekan sekali lagi untuk keluar.",
                            Toast.LENGTH_SHORT
                        ).show()
                        lastPressedTime = event.eventTime
                    }
                    return true
                }
            }
        }
        return false
    }
}