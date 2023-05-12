package com.sobarna.smartcalculatorapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
import com.sobarna.smartcalculatorapp.data.entity.CalculatorEntity
import com.sobarna.smartcalculatorapp.utils.Utils.getMasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class SecurityDataHandler @Inject constructor(@ApplicationContext private val context: Context) {

    fun encryptFile(calculatorEntity: CalculatorEntity) {
        val timestamp = System.currentTimeMillis()
        val file = File(context.filesDir, "encrypted_calculator_$timestamp.txt")

        val encryptedFile = EncryptedFile.Builder(
            file,
            context,
            getMasterKey(),
            AES256_GCM_HKDF_4KB
        ).build()

        val input = "${calculatorEntity.input}===${calculatorEntity.output}"

        encryptedFile.openFileOutput().use { outputStream ->
            outputStream.write(input.toByteArray(StandardCharsets.UTF_8))
        }
    }

    fun decryptFiles(): LiveData<List<CalculatorEntity>> {
        val mutableLiveData = MutableLiveData<List<CalculatorEntity>>()
        val fileNames =
            context.filesDir.list { _, name ->
                name.startsWith("encrypted_calculator_")
            }
        val calculatorEntities = mutableListOf<CalculatorEntity>()

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
                for (i in split.indices step 2) {
                    val input = split.getOrNull(i) ?: ""
                    val output = split.getOrNull(i + 1) ?: "0.0"

                    calculatorEntities.add(
                        CalculatorEntity(
                            input = input,
                            output = output.toDoubleOrNull() ?: 0.0
                        )
                    )
                }
            }
        }
        mutableLiveData.postValue(calculatorEntities)

        return mutableLiveData
    }
}

