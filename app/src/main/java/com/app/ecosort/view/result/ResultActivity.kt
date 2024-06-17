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
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.ecosort.R
import com.app.ecosort.ViewModelFactoryPrivate
import com.app.ecosort.api.ApiConfig
import com.app.ecosort.data.pref.UserPreference
import com.app.ecosort.data.pref.dataStore
import com.app.ecosort.databinding.ActivityResultBinding
import com.app.ecosort.response.UploadResponse
import com.app.ecosort.view.gallery.GalleryActivity
import com.app.ecosort.view.history.HistoryViewModel
import com.app.ecosort.view.home.MainActivity
import com.bumptech.glide.Glide
import com.dicoding.asclepius.database.History
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var history: History? = null

    private val historyViewModel by viewModels<HistoryViewModel>(){
        ViewModelFactoryPrivate.getInstance(application)
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
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
        val hasil = intent.getStringExtra(GalleryActivity.EXTRA_DETAIL)
        Log.d("image result", "onCreate: $image")
        Log.d("hasil result", "onCreate: $hasil")

        Glide.with(this@ResultActivity)
            .load(image)
            .into(binding.previewImageView)

        binding.tvResult.text = hasil

        binding.btnRetake.setOnClickListener{
            startActivity(Intent(this@ResultActivity, GalleryActivity::class.java))
            finish()
        }
        binding.btnToMain.setOnClickListener{
            startActivity(Intent(this@ResultActivity, MainActivity::class.java))
            finish()
        }

        if(history == null){
            history = History()
        }
        val timestamp = System.currentTimeMillis()
        val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(timestamp))
        history.let{
            it?.image = image.toString()
            it?.description = hasil.toString()
            it?.timestamp = formattedDate.toString()
        }
        historyViewModel.insert(history as History)
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

    companion object{
        const val EXTRA_HISTORY = "ResultActivity"
    }
}