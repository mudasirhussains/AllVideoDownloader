package com.example.allvideodownloader.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.allvideodownloader.MainActivity
import com.example.allvideodownloader.databinding.ActivityIntroScreenBinding

class IntroScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntroScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        clickListeners()
    }

    private fun clickListeners() {
        binding.buttonGetStarted.setOnClickListener {
            startActivity(Intent(this@IntroScreenActivity, MainActivity::class.java))
        }
    }

    private fun setBinding() {
        binding = ActivityIntroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}