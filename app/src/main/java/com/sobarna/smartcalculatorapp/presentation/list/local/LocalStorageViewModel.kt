package com.sobarna.smartcalculatorapp.presentation.list.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sobarna.smartcalculatorapp.domain.usecase.CalculatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocalStorageViewModel @Inject constructor(private val useCase: CalculatorUseCase) : ViewModel() {

    fun getAllHistory() = useCase.getListHistory(false).asLiveData()
}