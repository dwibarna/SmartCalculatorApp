package com.sobarna.smartcalculatorapp.di

import com.sobarna.smartcalculatorapp.domain.usecase.CalculatorUseCase
import com.sobarna.smartcalculatorapp.domain.usecase.CalculatorUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideCalculatorUseCae(useCase: CalculatorUseCase) : CalculatorUseCaseImpl
}