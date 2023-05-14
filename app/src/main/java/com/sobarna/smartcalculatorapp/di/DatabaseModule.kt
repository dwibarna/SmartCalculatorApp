package com.sobarna.smartcalculatorapp.di

import android.content.Context
import androidx.room.Room
import com.sobarna.smartcalculatorapp.data.room.CalculatorDao
import com.sobarna.smartcalculatorapp.data.room.CalculatorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : CalculatorDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = CalculatorDatabase::class.java,
            name = "calculator.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCalculatorDao(database: CalculatorDatabase): CalculatorDao = database.calculatorDao()
}
