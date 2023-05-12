package com.sobarna.smartcalculatorapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity
import com.sobarna.smartcalculatorapp.databinding.ContentAdapterListBinding

class CalculatorAdapter :ListAdapter<CalculatorEntity,CalculatorAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ContentAdapterListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(calculatorEntity: CalculatorEntity) {
            with(binding) {
                tvInput.text = calculatorEntity.input
                tvOutput.text = calculatorEntity.output.toString()
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

    companion object DiffCallback : DiffUtil.ItemCallback<CalculatorEntity>() {
        override fun areItemsTheSame(oldItem: CalculatorEntity, newItem: CalculatorEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CalculatorEntity, newItem: CalculatorEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }
}