package com.sobarna.smartcalculatorapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculatorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOperation(calculatorEntity: CalculatorEntity)

    @Query("SELECT* FROM calculator")
    fun getAllOperations(): Flow<List<CalculatorEntity>>
}