package com.app.ecosort.view.history

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.ecosort.R
import com.app.ecosort.databinding.ActivityHistoryDetailBinding
import com.app.ecosort.helper.PrefHelper
import com.bumptech.glide.Glide

class HistoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        overridePendingTransition(0, 0)

        supportActionBar?.hide()

        Glide.with(this@HistoryDetailActivity)
            .load(intent.getStringExtra(EXTRA_HISTORY_IMAGE))
            .into(binding.previewImageViewHistory)

        binding.tvResultHistory.text = intent.getStringExtra(EXTRA_HISTORY_DETAIL)

        val toolbar = binding.toolbar
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, android.R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val typeface: Typeface? = ResourcesCompat.getFont(this, R.font.montserrat_semibold)

        if (typeface != null) {
            val spannableTitle = SpannableString(toolbar.title)
            spannableTitle.setSpan(
                TypefaceSpan(typeface),
                0,
                spannableTitle.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            toolbar.title = spannableTitle
            toolbar.setTitleTextColor(Color.WHITE)
        }

        setupView()
    }

    companion object{
        const val EXTRA_HISTORY_IMAGE = "extra_history_image"
        const val EXTRA_HISTORY_DETAIL = "extra_history_detail"
    }

    private fun updateTheme() {
        val pref = PrefHelper(this)
        val isDarkModeEnabled = pref.getBoolean("dark_mode")
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        updateTheme()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
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