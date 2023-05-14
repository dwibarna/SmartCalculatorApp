package com.sobarna.smartcalculatorapp.data.secure

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
import com.sobarna.smartcalculatorapp.domain.model.Calculator
import com.sobarna.smartcalculatorapp.utils.Utils.getMasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class SecurityDataHandler @Inject constructor(@ApplicationContext private val context: Context) {

    fun encryptFile(calculator: Calculator) {
        val timestamp = System.currentTimeMillis()
        val file = File(context.filesDir, "encrypted_calculator_$timestamp.txt")

        val encryptedFile = EncryptedFile.Builder(
            file,
            context,
            getMasterKey(),
            AES256_GCM_HKDF_4KB
        ).build()

        val input = "$timestamp===${calculator.input}===${calculator.output}"

        encryptedFile.openFileOutput().use { outputStream ->
            outputStream.write(input.toByteArray(StandardCharsets.UTF_8))
        }
    }

    fun decryptFiles(): Flow<List<Calculator>> {
        val mutableLiveData = MutableLiveData<List<Calculator>>()
        val fileNames =
            context.filesDir.list { _, name ->
                name.startsWith("encrypted_calculator_")
            }
        val calculatorList = mutableListOf<Calculator>()

        fileNames?.forEach { fileName ->
            val file = File(context.filesDir, fileName)

            val encryptedFile = EncryptedFile.Builder(
                file, context,
                getMasterKey(),
                AES256_GCM_HKDF_4KB
            ).build()
            val result = encryptedFile.openFileInput().use { inputStream ->
                inputStream.readBytes().toString(StandardCharsets.UTF_8)
            }
            val split = result.split("===")

            if (split.isNotEmpty()) {
                for (i in split.indices step 3) {
                    val id = split.getOrNull(i)
                    val input = split.getOrNull(i + 1) ?: ""
                    val output = split.getOrNull(i + 2) ?: "0.0"

                    calculatorList.add(
                        Calculator(
                            id = id?.toLongOrNull() ?: 0,
                            input = input,
                            output = output.toDoubleOrNull() ?: 0.0
                        )
                    )
                }
            }
        }
        mutableLiveData.postValue(calculatorList)

        return mutableLiveData.asFlow()
    }
}

