package com.sobarna.smartcalculatorapp.domain.usecase

import android.graphics.Bitmap
import com.sobarna.smartcalculatorapp.data.Result
import com.sobarna.smartcalculatorapp.data.repository.CalculatorRepository
import com.sobarna.smartcalculatorapp.domain.model.Calculator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CalculatorUseCase @Inject constructor(private val repository: CalculatorRepository) : CalculatorUseCaseImpl {

    override fun scanData(bitmap: Bitmap?, stateLocalStorage: Boolean): Flow<Result<Calculator>> {
        return repository.scanData(bitmap, stateLocalStorage)
    }

    override fun getListHistory(stateLocalStorage: Boolean): Flow<Result<List<Calculator>>> {
        return repository.getListHistory(stateLocalStorage)
    }
}