package com.sobarna.smartcalculatorapp.domain.usecase

import android.graphics.Bitmap
import com.sobarna.smartcalculatorapp.data.Result
import com.sobarna.smartcalculatorapp.domain.model.Calculator
import kotlinx.coroutines.flow.Flow

interface CalculatorUseCaseImpl {

    fun scanData(bitmap: Bitmap?, stateLocalStorage: Boolean): Flow<Result<Calculator>>

    fun getListHistory(stateLocalStorage: Boolean): Flow<Result<List<Calculator>>>
}