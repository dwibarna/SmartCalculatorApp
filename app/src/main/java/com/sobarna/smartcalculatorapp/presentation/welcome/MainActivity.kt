package com.sobarna.smartcalculatorapp.presentation.welcome

import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sobarna.smartcalculatorapp.databinding.ActivityMainBinding
import com.sobarna.smartcalculatorapp.presentation.list.CalculatorListActivity
import com.sobarna.smartcalculatorapp.utils.Utils.setViewAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAnimation()
        initClick()
    }

    private fun initClick() {
        binding.btnStart.setOnClickListener {
            Intent(this@MainActivity, CalculatorListActivity::class.java).let(::startActivity)
            finish()
        }
    }

    private fun setupAnimation() {
        with(binding) {
            AnimatorSet().apply {
                playSequentially(
                    setViewAnimation(imageView, 1f, 500),
                    setViewAnimation(textView, 1f, 500),
                    setViewAnimation(btnStart, 1f, 500)
                )
                startDelay = 500
            }.start()
        }
    }



}
