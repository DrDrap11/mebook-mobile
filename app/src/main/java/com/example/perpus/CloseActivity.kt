package com.example.perpus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.perpus.databinding.ActivityMainBinding
import com.example.perpus.databinding.DialogSignoutBinding

class CloseActivity : AppCompatActivity() {
    private var binding: DialogSignoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSignoutBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar!!.hide()
        finish()
    }
}