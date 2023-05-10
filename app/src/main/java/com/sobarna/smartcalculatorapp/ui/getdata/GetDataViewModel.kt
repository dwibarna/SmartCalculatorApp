package com.sobarna.smartcalculatorapp.ui.getdata

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.sobarna.smartcalculatorapp.data.CalculatorRepository

class GetDataViewModel(private val repository: CalculatorRepository): ViewModel() {

    fun scanCalculator(bitmap: Bitmap?) = repository.scanData(bitmap)

}