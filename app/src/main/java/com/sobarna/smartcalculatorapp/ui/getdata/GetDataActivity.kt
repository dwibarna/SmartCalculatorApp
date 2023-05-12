package com.sobarna.smartcalculatorapp.ui.getdata

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sobarna.smartcalculatorapp.data.Result
import com.sobarna.smartcalculatorapp.databinding.ActivityGetDataBinding
import com.sobarna.smartcalculatorapp.utils.Utils.displayImageBitMap
import com.sobarna.smartcalculatorapp.utils.Utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetDataBinding

    private val viewModel: GetDataViewModel by viewModels()
    private var initState: Boolean = true

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { result ->
        setResultCalculator(result,initState)
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
                ,initState
                )
            }
        }
    }

    private fun setResultCalculator(result: Bitmap?,state: Boolean) {
            viewModel.scanCalculator(
                result,
                !state
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

                            binding.tvInput.text = it.input
                            binding.tvOutput.text = it.output.toString()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchState.setOnCheckedChangeListener { _, b ->
            initState = !b
        }

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
        } else if (requestCode == REQUEST_CODE_PERMISSIONS) {
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