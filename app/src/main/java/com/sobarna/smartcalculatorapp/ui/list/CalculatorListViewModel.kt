package com.sobarna.smartcalculatorapp.ui.list

import androidx.lifecycle.ViewModel
import com.sobarna.smartcalculatorapp.data.CalculatorRepository

class CalculatorListViewModel(private val repository: CalculatorRepository) : ViewModel() {

    fun getAllHistory() = repository.getListHistory()
}