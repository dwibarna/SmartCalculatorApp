package com.sobarna.smartcalculatorapp.data.repository

import android.graphics.Bitmap
import com.sobarna.smartcalculatorapp.domain.model.Calculator
import com.sobarna.smartcalculatorapp.data.Result
import kotlinx.coroutines.flow.Flow

interface CalculatorRepositoryImpl {

    fun scanData(bitmap: Bitmap?, stateLocalStorage: Boolean): Flow<Result<Calculator>>

    fun getListHistory(stateLocalStorage: Boolean): Flow<Result<List<Calculator>>>
}