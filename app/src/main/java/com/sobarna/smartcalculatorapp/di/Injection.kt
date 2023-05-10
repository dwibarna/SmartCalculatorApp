package com.sobarna.smartcalculatorapp.di

import android.content.Context
import com.sobarna.smartcalculatorapp.data.room.CalculatorDatabase
import com.sobarna.smartcalculatorapp.data.CalculatorRepository

object Injection {

    fun provideRepository(context: Context): CalculatorRepository {
        return CalculatorRepository.getInstance(
            dao = CalculatorDatabase.getInstance(context).calculatorDao()
        )
    }
}