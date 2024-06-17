package com.app.ecosort.view.history

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.ecosort.R
import com.app.ecosort.databinding.ActivityHistoryBinding
import com.app.ecosort.databinding.ActivityHistoryDetailBinding
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

        Glide.with(this@HistoryDetailActivity)
            .load(intent.getStringExtra(EXTRA_HISTORY_IMAGE))
            .into(binding.previewImageViewHistory)

        binding.tvResultHistory.text = intent.getStringExtra(EXTRA_HISTORY_DETAIL)
    }

    companion object{
        const val EXTRA_HISTORY_IMAGE = "extra_history_image"
        const val EXTRA_HISTORY_DETAIL = "extra_history_detail"
    }
}