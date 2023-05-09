package com.sobarna.smartcalculatorapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sobarna.smartcalculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAnimation()
        initClick()
    }

    private fun initClick() {
        binding.btnStart.setOnClickListener {
            Intent(this@MainActivity,CalculatorListActivity::class.java).let(::startActivity)
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

    private fun setViewAnimation(view: View, alpha: Float, duration: Long) =
        ObjectAnimator.ofFloat(view, View.ALPHA, alpha).setDuration(duration)

}
