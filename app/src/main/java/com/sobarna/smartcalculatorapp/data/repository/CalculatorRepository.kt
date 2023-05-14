package com.sobarna.smartcalculatorapp.data.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.sobarna.smartcalculatorapp.data.Result
import com.sobarna.smartcalculatorapp.data.secure.SecurityDataHandler
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity
import com.sobarna.smartcalculatorapp.data.room.CalculatorDao
import com.sobarna.smartcalculatorapp.domain.model.Calculator
import com.sobarna.smartcalculatorapp.utils.DataMapper.mapperDomainToEntity
import com.sobarna.smartcalculatorapp.utils.DataMapper.mapperListEntityToListDomain
import com.sobarna.smartcalculatorapp.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalculatorRepository @Inject constructor(
    private val dao: CalculatorDao,
    private val securityDataHandler: SecurityDataHandler
) : CalculatorRepositoryImpl {
    override fun getListHistory(stateLocalStorage: Boolean): Flow<Result<List<Calculator>>> =
        flow {
            emit(Result.Loading)
            try {
                if (stateLocalStorage) {
                    emitAll(dao.getAllOperations().map {
                        Result.Success(mapperListEntityToListDomain(it))
                    })
                } else {
                    flow {
                        emitAll(securityDataHandler.decryptFiles().map {
                            Result.Success(it)
                        })
                    }.flowOn(Dispatchers.IO).collect {
                        emit(it)
                    }
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    override fun scanData(
        bitmap: Bitmap?,
        stateLocalStorage: Boolean
    ): Flow<Result<Calculator>> = flow {
        emit(Result.Loading)
        val textRecognizer = TextRecognition.getClient()
        val image = bitmap?.let { InputImage.fromBitmap(it, 0) }
        val mutable: MutableLiveData<Calculator> = MutableLiveData()

        try {
            if (image != null) {
                textRecognizer.process(image).addOnSuccessListener { text ->
                    val operations = Utils.resultCalculator(text.text)
                    Calculator(
                        id = null,
                        input = operations.first,
                        output = operations.second
                    ).let {
                        mutable.value = it
                        CoroutineScope(Dispatchers.IO).launch {
                            if (stateLocalStorage)
                                insertOperations(mapperDomainToEntity(it))
                            else
                                securityDataHandler.encryptFile(it)
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

        val success: LiveData<Result<Calculator>> = mutable.map {
            Result.Success(it)
        }
        emitAll(success.asFlow())
    }

    private suspend fun insertOperations(value: CalculatorEntity?) {
        withContext(Dispatchers.IO) {
            if (value != null) {
                dao.insertOperation(value)
            }
        }
    }
}