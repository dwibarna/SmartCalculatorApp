package com.sobarna.smartcalculatorapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity
import com.sobarna.smartcalculatorapp.databinding.ContentAdapterListBinding

class CalculatorAdapter(private val list: List<CalculatorEntity>) :
    RecyclerView.Adapter<CalculatorAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ContentAdapterListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(calculatorEntity: CalculatorEntity) {
            with(binding) {
                textView.text = "Result:  ${calculatorEntity.input}"
                textView1.text = "Output:  ${calculatorEntity.output}"
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

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }
}