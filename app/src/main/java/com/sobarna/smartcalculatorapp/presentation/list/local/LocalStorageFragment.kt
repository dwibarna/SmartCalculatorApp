package com.sobarna.smartcalculatorapp.presentation.list.local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sobarna.smartcalculatorapp.data.Result
import com.sobarna.smartcalculatorapp.databinding.FragmentLocalStorageBinding
import com.sobarna.smartcalculatorapp.domain.model.Calculator
import com.sobarna.smartcalculatorapp.presentation.list.CalculatorAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocalStorageFragment : Fragment() {

    private val viewModel: LocalStorageViewModel by viewModels()

    private var _binding: FragmentLocalStorageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocalStorageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllHistory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
                Result.Loading -> binding.progressBar.isVisible = true
                is Result.Success -> {
                    binding.progressBar.isVisible = false
                    initAdapter(result.data.sortedByDescending { it.id })
                }
            }
        }
    }

    private fun initAdapter(list: List<Calculator>) {
        val contentAdapter = CalculatorAdapter()
        contentAdapter.submitList(list)

        if (list.isEmpty()) {
            binding.tvEmptyData.visibility = View.VISIBLE
        } else {
            binding.tvEmptyData.visibility = View.GONE
            binding.rvItem.apply {
                adapter = contentAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}