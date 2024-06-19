package com.app.ecosort.view.gallery

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.ecosort.R
import com.app.ecosort.ResultState
import com.app.ecosort.ViewModelFactoryGallery
import com.app.ecosort.databinding.ActivityGalleryBinding
import com.app.ecosort.getImageUri
import com.app.ecosort.reduceFileImage
import com.app.ecosort.response.ImageDetailItem
import com.app.ecosort.uriToFile
import com.app.ecosort.view.result.ResultActivity


class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<GalleryViewModel> {
        ViewModelFactoryGallery.getInstance(this)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                startCamera()
            } else {
                showToast("Permission denied to access camera")
            }
        }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                startCamera()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }



    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupView()
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { checkCameraPermission() }
        binding.analyzeButton.setOnClickListener { uploadImage() }

    }

    private fun uploadImage(){
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(currentImageUri!!, this).reduceFileImage()
            Log.d(imageFile.path, "ini image mau dikirim")

            viewModel.uploadImage(imageFile).observe(this) { result ->

                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            binding.loading.visibility = View.VISIBLE
                            binding.analyzeButton.isEnabled = false
                            binding.galleryButton.isEnabled = false
                            binding.cameraButton.isEnabled = false
                        }

                        is ResultState.Success -> {
                            val detail: List<ImageDetailItem>? = result.data.imageDetail

                            val hasil = if (detail != null) {
                                val halfSize = detail.size / 2
                                detail.subList(0, halfSize).withIndex().joinToString("\n") { (index, item) ->
                                    "Object ${index + 1} = ${item.confidence.toString().substring(2, 4)}% ${item.classification} (${item.sorting})"
                                }
                            } else {
                                "Sampah Tak Terdeteksi"
                            }
                            Log.d("Hasil", "uploadImage: $hasil")

                            val intent = Intent(this@GalleryActivity, ResultActivity::class.java)
                            intent.putExtra(EXTRA_IMAGE, result.data.image)
                            intent.putExtra(EXTRA_DETAIL, hasil)

                            binding.loading.visibility = View.GONE
                            showToast(result.data.messages)
                            startActivity(intent)
                            finish()
                        }

                        is ResultState.Error -> {
                            showToast(result.error)
                        }
                    }
                }
            }
        } ?: showToast("Pilih Foto dulu")
    }
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.action_bar)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    companion object {
        const val EXTRA_DETAIL = "extra detail"
        const val EXTRA_IMAGE = "extra image"

    }
}