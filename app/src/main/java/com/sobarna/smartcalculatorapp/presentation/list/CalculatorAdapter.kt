package com.sobarna.smartcalculatorapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sobarna.smartcalculatorapp.databinding.ContentAdapterListBinding
import com.sobarna.smartcalculatorapp.domain.model.Calculator

class CalculatorAdapter : ListAdapter<Calculator, CalculatorAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ContentAdapterListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(calculator: Calculator) {
            with(binding) {
                tvInput.text = calculator.input
                tvOutput.text = calculator.output.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContentAdapterListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.bindItem(it)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Calculator>() {
        override fun areItemsTheSame(oldItem: Calculator, newItem: Calculator): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Calculator, newItem: Calculator): Boolean {
            return oldItem.id == newItem.id
        }
    }
}