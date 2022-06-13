package com.example.perpus

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailKonfirmasi : AppCompatActivity() {

    private lateinit var tvNotif : TextView
    private lateinit var btLogin : Button
    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_konfirmasi)

        tvNotif = findViewById(R.id.tvNotif)
        btLogin = findViewById(R.id.login)
        tvNotif.text = intent.getStringExtra("notif").toString()
        id = intent.getStringExtra("id").toString()
//        Toast.makeText(this, id, Toast.LENGTH_LONG).show()

        btLogin.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            myIntent.putExtra("intent", id)
            startActivity(myIntent)
            finish()
        }

    }
}