package com.sobarna.smartcalculatorapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sobarna.smartcalculatorapp.data.CalculatorRepository
import com.sobarna.smartcalculatorapp.di.Injection
import com.sobarna.smartcalculatorapp.ui.getdata.GetDataViewModel
import com.sobarna.smartcalculatorapp.ui.list.CalculatorListViewModel


class ViewModelFactory private constructor(
    private val repository: CalculatorRepository
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetDataViewModel::class.java))
            return GetDataViewModel(repository) as T
        else if (modelClass.isAssignableFrom(CalculatorListViewModel::class.java))
            return CalculatorListViewModel(repository) as T
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class: +${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}