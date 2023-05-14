package com.sobarna.smartcalculatorapp.presentation.getdata

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sobarna.smartcalculatorapp.domain.usecase.CalculatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetDataViewModel @Inject constructor(private val useCase: CalculatorUseCase) : ViewModel() {

    fun scanCalculator(bitmap: Bitmap?, state: Boolean) = useCase.scanData(bitmap, state).asLiveData()

}