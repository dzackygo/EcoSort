package com.app.ecosort.view.result

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.ecosort.R
import com.app.ecosort.api.ApiConfig
import com.app.ecosort.data.pref.UserPreference
import com.app.ecosort.data.pref.dataStore
import com.app.ecosort.databinding.ActivityResultBinding
import com.app.ecosort.response.UploadResponse
import com.app.ecosort.view.gallery.GalleryActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupView()


        val image = intent.getStringExtra(GalleryActivity.EXTRA_IMAGE)
        val sorting = intent.getStringExtra(GalleryActivity.EXTRA_SORTING)
        val classification = intent.getStringExtra(GalleryActivity.EXTRA_CLASSIFICATION)
        val confidence = intent.getStringExtra(GalleryActivity.EXTRA_CONFIDENCE)

//        if (image != null && sorting != null && classification != null && confidence != null) {
            Glide.with(this@ResultActivity)
                .load(image)
                .into(binding.previewImageView)

            binding.tvResult.text =
                    "$confidence $classification ($sorting)"
//        }
//        else {
//            finish()
//            val intent = Intent(this@ResultActivity, GalleryActivity::class.java)
//            startActivity(intent)
//        }
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
}