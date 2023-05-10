package com.sobarna.smartcalculatorapp.data

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity
import com.sobarna.smartcalculatorapp.data.room.CalculatorDao
import com.sobarna.smartcalculatorapp.utils.Utils
import kotlinx.coroutines.*

class CalculatorRepository private constructor(private val dao: CalculatorDao) {

    fun getListHistory(): LiveData<Result<List<CalculatorEntity>>> = liveData {
        emit(Result.Loading)
        try {
            emitSource(dao.getAllOperations().map {
                Result.Success(it)
            })
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun scanData(bitmap: Bitmap?): LiveData<Result<CalculatorEntity>> = liveData {
        emit(Result.Loading)
        val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = bitmap?.let { InputImage.fromBitmap(it, 0) }
        val mutable: MutableLiveData<CalculatorEntity> = MutableLiveData()

        try {
            if (image != null) {
                textRecognizer.process(image).addOnSuccessListener { text ->
                    val operations = Utils.resultCalculator(text.text)
                    CalculatorEntity(
                        input = operations.first,
                        output = operations.second
                    ).let {
                        mutable.value = it
                        CoroutineScope(Dispatchers.IO).launch {
                            insertOperations(it)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            var message: String? = null
            if (image != null) {
                textRecognizer.process(image).addOnFailureListener { s ->
                    message = s.message
                }
            }
            emit(Result.Error(message.toString()))
        }

        val success: LiveData<Result<CalculatorEntity>> = mutable.map {
            Result.Success(it)
        }
        emitSource(success)
    }

    private suspend fun insertOperations(value: CalculatorEntity?) {
        withContext(Dispatchers.IO) {
            if (value != null) {
                dao.insertOperation(value)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: CalculatorRepository? = null

        fun getInstance(dao: CalculatorDao): CalculatorRepository = instance ?: synchronized(this) {
            instance ?: CalculatorRepository(dao)
        }.also { instance = it }
    }
}
