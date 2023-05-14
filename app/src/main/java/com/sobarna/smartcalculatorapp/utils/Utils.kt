package com.sobarna.smartcalculatorapp.utils

import android.animation.ObjectAnimator
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.View
import android.widget.ImageView
import androidx.security.crypto.MasterKeys
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sobarna.smartcalculatorapp.BuildConfig
import org.mariuszgromada.math.mxparser.Expression
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private val timeStamp: String = SimpleDateFormat(
        "dd MMM yyyy",
        Locale.US
    ).format(System.currentTimeMillis())

    fun setViewAnimation(view: View, alpha: Float, duration: Long) =
        ObjectAnimator.ofFloat(view, View.ALPHA, alpha).setDuration(duration)
    fun resultCalculator(input: String): Pair<String, Double> {
        val lines = input.lines()
        var arithmeticInput = ""

        lines.forEach {
            val cleanLine = it.replace(Regex("[^\\d+\\-*/]"), "")
            if (cleanLine.matches(Regex("[\\d+\\-*/]+"))) {
                arithmeticInput = cleanLine
                return@forEach
            }
        }

        return if (arithmeticInput.isNotBlank())
            Pair(arithmeticInput, Expression(arithmeticInput).calculate())
        else
            Pair("0", 0.0)
    }

    private fun createCustomTempFile(context: Context): File {
        val storageDirection: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDirection)
    }

    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val file = createCustomTempFile(context)
        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream = FileOutputStream(file)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0)
            outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return file
    }

    fun displayImageBitMap(
        context: Context,
        uri: Any,
        imageView: ImageView
    ) {
        Glide.with(context)
            .asBitmap()
            .load(uri)
            .apply(RequestOptions().fitCenter())
            .into(imageView)
    }

    fun getMasterKey() =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    } else {
        BuildConfig.MASTER_KEY_PRE_M
    }
}