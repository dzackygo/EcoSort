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
import com.app.ecosort.ViewModelFactory
import com.app.ecosort.databinding.ActivityGalleryBinding
import com.app.ecosort.reduceFileImage
import com.app.ecosort.uriToFile
import com.app.ecosort.view.camera.CameraActivity
import com.bumptech.glide.Glide

class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<GalleryViewModel> {
        ViewModelFactory.getInstance(this)
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

        currentImageUri = Uri.parse(intent.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE))
        Glide.with(this)
            .load(currentImageUri)
            .into(binding.previewImageView)

        binding.analyzeButton.setOnClickListener { uploadImage() }
    }

    private fun uploadImage(){
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")

//            viewModel.uploadImage(imageFile, description).observe(this) { result ->
//                if (result != null) {
//                    when (result) {
//                        is ResultState.Loading -> {
//                            showLoading(true)
//                        }
//
//                        is ResultState.Success -> {
//                            showToast(result.data.message)
//                            showLoading(false)
//                            startActivity(Intent(this@UploadActivity, MainActivity::class.java))
//                            finish()
//                        }
//
//                        is ResultState.Error -> {
//                            showToast(result.error)
//                        }
//                    }
//                }
//            }
        } ?: showToast("Pilih Foto dulu")
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}