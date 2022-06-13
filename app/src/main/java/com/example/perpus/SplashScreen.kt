package com.example.perpus;


import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.perpus.ui.login.LoginActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var countdown_timer: CountDownTimer
    var time_in_milli_seconds = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
//        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        startTimer(time_in_milli_seconds)

//        getSharedPreferences("login", MODE_PRIVATE)
//                .edit()
//                .putString("code", "82259500319")
//                .apply()

    }

    private fun startTimer(time_in_seconds: Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                user()
            }
            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
            }
        }
        countdown_timer.start()
    }

    private fun user() {
        val myPreferences = getSharedPreferences("login", MODE_PRIVATE)
        val key = myPreferences.getString("code", null)
        if (key != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
    

