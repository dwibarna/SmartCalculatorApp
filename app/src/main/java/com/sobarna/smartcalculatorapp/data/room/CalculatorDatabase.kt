package com.sobarna.smartcalculatorapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity

@Database(
    entities = [CalculatorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CalculatorDatabase:RoomDatabase() {

    abstract fun calculatorDao(): CalculatorDao

    companion object {
        @Volatile
        private var instance: CalculatorDatabase? = null

        fun getInstance(context:Context): CalculatorDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context = context,
                    klass = CalculatorDatabase::class.java,
                    name = "calculator.db"
                ).build()
            }.also { instance = it }
    }

}