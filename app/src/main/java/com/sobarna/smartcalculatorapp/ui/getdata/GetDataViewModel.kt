package com.sobarna.smartcalculatorapp.ui.getdata

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.sobarna.smartcalculatorapp.data.repository.CalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetDataViewModel @Inject constructor(private val repository: CalculatorRepository): ViewModel() {

    fun scanCalculator(bitmap: Bitmap?) = repository.scanData(bitmap)

}