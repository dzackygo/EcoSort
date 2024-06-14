package com.app.ecosort.view.gallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.ecosort.R
import com.app.ecosort.ResultState
import com.app.ecosort.ViewModelFactoryGallery
import com.app.ecosort.databinding.ActivityGalleryBinding
import com.app.ecosort.getImageUri
import com.app.ecosort.reduceFileImage
import com.app.ecosort.uriToFile
import com.app.ecosort.view.camera.CameraActivity
import com.app.ecosort.view.result.ResultActivity
import com.bumptech.glide.Glide

class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<GalleryViewModel> {
        ViewModelFactoryGallery.getInstance(this)
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

        supportActionBar?.hide()

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.analyzeButton.setOnClickListener { uploadImage() }

    }

    private fun uploadImage(){
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d(imageFile.toString(), "showImage: ${imageFile.path}")

            viewModel.uploadImage(imageFile).observe(this) { result ->
//                startActivity(Intent(this@GalleryActivity, ResultActivity::class.java))
//                finish()

                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {

                        }

                        is ResultState.Success -> {
                            startActivity(Intent(this@GalleryActivity, ResultActivity::class.java))
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
}