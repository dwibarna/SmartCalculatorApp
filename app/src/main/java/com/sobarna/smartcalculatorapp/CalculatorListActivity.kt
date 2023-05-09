package com.sobarna.smartcalculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sobarna.smartcalculatorapp.databinding.ActivityCalculatorListBinding

class CalculatorListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}