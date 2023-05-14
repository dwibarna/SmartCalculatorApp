package com.sobarna.smartcalculatorapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculator")
data class CalculatorEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long = 0,

    @ColumnInfo("input")
    val input:String?,

    @ColumnInfo("output")
    val output:Double?
)