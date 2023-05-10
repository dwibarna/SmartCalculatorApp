package com.sobarna.smartcalculatorapp.ui.list

import androidx.lifecycle.ViewModel
import com.sobarna.smartcalculatorapp.data.repository.CalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculatorListViewModel @Inject constructor(private val repository: CalculatorRepository) : ViewModel() {

    fun getAllHistory() = repository.getListHistory()
}