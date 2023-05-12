package com.sobarna.smartcalculatorapp.ui.list

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
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity
import com.sobarna.smartcalculatorapp.databinding.FragmentRoomStorageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomStorageFragment : Fragment() {

    private val viewModel: RoomStorageViewModel by viewModels()

    private var _binding: FragmentRoomStorageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomStorageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllHistory().observe(viewLifecycleOwner) { result->
            when (result) {
                is Result.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
                Result.Loading -> {binding.progressBar.isVisible = true}
                is Result.Success -> {
                    binding.progressBar.isVisible = false
                    initAdapter(result.data)
                }
            }
        }
    }

    private fun initAdapter(list: List<CalculatorEntity>) {
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