package com.sobarna.smartcalculatorapp.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sobarna.smartcalculatorapp.data.Result
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity
import com.sobarna.smartcalculatorapp.databinding.ActivityCalculatorListBinding
import com.sobarna.smartcalculatorapp.ui.getdata.GetDataActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculatorListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorListBinding
    private val viewModel: CalculatorListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            Intent(this@CalculatorListActivity, GetDataActivity::class.java).let(::startActivity)
        }

        viewModel.getAllHistory().observe(this) { result ->
            when (result) {
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    initAdapter(result.data)
                }
            }
        }

    }

    private fun initAdapter(list: List<CalculatorEntity>) {
        if (list.isEmpty()) {
            binding.clEmptyList.visibility = View.VISIBLE
            binding.clContent.visibility = View.GONE
        } else {
            binding.clEmptyList.visibility = View.GONE
            binding.clContent.visibility = View.VISIBLE

            binding.rvItem.apply {
                adapter = CalculatorAdapter(list)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

}