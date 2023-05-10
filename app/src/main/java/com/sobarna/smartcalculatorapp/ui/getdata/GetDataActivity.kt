package com.sobarna.smartcalculatorapp.ui.getdata

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.sobarna.smartcalculatorapp.data.Result
import com.sobarna.smartcalculatorapp.ui.ViewModelFactory
import com.sobarna.smartcalculatorapp.utils.Utils.displayImageBitMap
import com.sobarna.smartcalculatorapp.utils.Utils.uriToFile
import com.sobarna.smartcalculatorapp.databinding.ActivityGetDataBinding

class GetDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetDataBinding
    private lateinit var viewModel: GetDataViewModel
    private lateinit var factory: ViewModelFactory

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { result ->
        setResultCalculator(result)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImage = result.data?.data as Uri
            selectedImage.let {
                val file = uriToFile(it, this)
                setResultCalculator(
                    BitmapFactory.decodeFile(file.path)
                )
            }
        }
    }

    private fun setResultCalculator(result: Bitmap?) {
        viewModel.scanCalculator(
            result
        ).observe(this) { res ->
            when (res) {
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    res.data.let {
                        displayImageBitMap(
                            applicationContext,
                            result ?: "",
                            binding.ivPreview
                        )
                        binding.textView99.text = "input: ${it.input}\noutput: ${it.output}"
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetDataBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[GetDataViewModel::class.java]
        setContentView(binding.root)

        /*
        binding.btnTakeData.setOnClickListener {
            if(!allPermissionStorageGranted())
                permissionUsageCamera()
            else
                launcherCamera.launch(null)
        }
         */
        binding.btnTakeData.setOnClickListener {
            if (!allPermissionCameraGranted())
                permissionUsageStorage()
            else
                Intent().apply {
                    action = Intent.ACTION_GET_CONTENT
                    type = "image/*"
                }.let {
                    launcherIntentGallery.launch(
                        Intent.createChooser(
                            it, "From Gallery"
                        )
                    )
                }
        }

    }

    private fun permissionUsageStorage() {
        if (!allPermissionCameraGranted())
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSION_STORAGE,
                REQUEST_CODE_PERMISSIONS
            )
    }

    private fun permissionUsageCamera() {
        if (!allPermissionCameraGranted())
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSION_CAMERA,
                REQUEST_CODE_PERMISSIONS_CAMERA
            )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS_CAMERA) {
            if (!allPermissionCameraGranted()) {
                Toast.makeText(this, "gak diijinin", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                launcherCamera.launch(
                    null
                )
            }
        } else if(requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionStorageGranted()) {
                Toast.makeText(this, "gak diijinin", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Intent().apply {
                    action = Intent.ACTION_GET_CONTENT
                    type = "image/*"
                }.let {
                    launcherIntentGallery.launch(
                        Intent.createChooser(
                            it, "From Gallery"
                        )
                    )
                }
            }
        }
    }

    private fun allPermissionCameraGranted() =
        REQUIRED_PERMISSION_CAMERA.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }

    private fun allPermissionStorageGranted() =
        REQUIRED_PERMISSION_STORAGE.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }

    companion object {
        private val REQUIRED_PERMISSION_CAMERA = arrayOf(android.Manifest.permission.CAMERA)
        private val REQUIRED_PERMISSION_STORAGE =
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        private const val REQUEST_CODE_PERMISSIONS = 89
        private const val REQUEST_CODE_PERMISSIONS_CAMERA = 99
    }
}