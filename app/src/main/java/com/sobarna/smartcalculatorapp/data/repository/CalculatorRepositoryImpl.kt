package com.sobarna.smartcalculatorapp.data.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.sobarna.smartcalculatorapp.data.Result
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity

interface CalculatorRepositoryImpl {

    fun scanData(bitmap: Bitmap?): LiveData<Result<CalculatorEntity>>

    fun getListHistory(): LiveData<Result<List<CalculatorEntity>>>
}