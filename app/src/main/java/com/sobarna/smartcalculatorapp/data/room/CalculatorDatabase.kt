package com.sobarna.smartcalculatorapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity

@Database(
    entities = [CalculatorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CalculatorDatabase:RoomDatabase() {

    abstract fun calculatorDao(): CalculatorDao

}