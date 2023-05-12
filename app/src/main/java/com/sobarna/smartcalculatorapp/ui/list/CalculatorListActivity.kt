package com.sobarna.smartcalculatorapp.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sobarna.smartcalculatorapp.R
import com.sobarna.smartcalculatorapp.databinding.ActivityCalculatorListBinding
import com.sobarna.smartcalculatorapp.ui.getdata.GetDataActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculatorListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            Intent(this@CalculatorListActivity, GetDataActivity::class.java).let(::startActivity)
        }

        initBottomNav()
    }
    private fun initBottomNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_room,
            R.id.nav_local
        ).build()

        setupActionBarWithNavController(
            navController = navController, appBarConfiguration
        )
        binding.bottomNavView.setupWithNavController(navController)
    }
}