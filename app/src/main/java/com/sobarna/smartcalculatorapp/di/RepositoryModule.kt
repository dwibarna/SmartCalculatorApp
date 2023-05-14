package com.sobarna.smartcalculatorapp.di

import com.sobarna.smartcalculatorapp.data.repository.CalculatorRepository
import com.sobarna.smartcalculatorapp.data.repository.CalculatorRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: CalculatorRepository) : CalculatorRepositoryImpl
}