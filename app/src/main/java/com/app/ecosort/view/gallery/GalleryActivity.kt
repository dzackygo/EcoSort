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
import com.app.ecosort.response.ImageDetailItem
import com.app.ecosort.response.UploadResponse
import com.app.ecosort.uriToFile
import com.app.ecosort.view.result.ResultActivity
import java.io.Serializable


class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    private var currentImageUri: Uri? = null
    private var sorting = ""
    private var classification = ""
    private var confidence = ""

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
            val imageFile = uriToFile(currentImageUri!!, this).reduceFileImage()
            Log.d(imageFile.path, "ini image mau dikirim")

            viewModel.uploadImage(imageFile).observe(this) { result ->

                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {

                        }

                        is ResultState.Success -> {
                            val detail: List<ImageDetailItem> = result.data.imageDetail

                            val highestConfidenceItem = detail.maxByOrNull { it.confidence }
                            if (highestConfidenceItem != null) {
                                sorting = highestConfidenceItem.sorting
                                classification = highestConfidenceItem.classification
                                confidence = highestConfidenceItem.confidence.toString()
                            }
                            val intent = Intent(this@GalleryActivity, ResultActivity::class.java)
                            intent.putExtra(EXTRA_IMAGE, result.data.image)
                            intent.putExtra(EXTRA_SORTING, sorting)
                            intent.putExtra(EXTRA_CLASSIFICATION, classification)
                            intent.putExtra(EXTRA_CONFIDENCE, confidence)
                            intent.putExtra(
                                EXTRA_DETAIL,
                                detail as Serializable
                            )
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
    companion object {
        const val EXTRA_DETAIL = "extra detail"
        const val EXTRA_IMAGE = "extra image"
        const val EXTRA_SORTING = "extra sorting"
        const val EXTRA_CLASSIFICATION = "extra classification"
        const val EXTRA_CONFIDENCE = "extra confidence"

    }
}