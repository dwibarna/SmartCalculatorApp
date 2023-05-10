package com.sobarna.smartcalculatorapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity

@Dao
interface CalculatorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOperation(calculatorEntity: CalculatorEntity)

    @Query("SELECT* FROM calculator")
    fun getAllOperations(): LiveData<List<CalculatorEntity>>
}