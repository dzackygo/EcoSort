package com.app.ecosort.view.news

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.ecosort.R
import com.app.ecosort.view.adapter.NewsAdapter
import com.app.ecosort.databinding.ActivityNewsBinding
import com.app.ecosort.helper.PrefHelper
import com.app.ecosort.view.camera.CameraActivity
import com.app.ecosort.view.history.HistoryActivity
import com.app.ecosort.view.home.MainActivity
import com.app.ecosort.view.settings.SettingsActivity

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val  pref by lazy { PrefHelper(this) }
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewsBinding.inflate(layoutInflater)
        updateTheme()
        setContentView(binding.root)
        overridePendingTransition(0, 0)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = binding.toolbar
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

        supportActionBar?.hide()

        binding.bottomNavView.selectedItemId = R.id.news

        binding.bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this,MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    true
                }
                R.id.news -> {
                    startActivity(Intent(this, NewsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    true
                }
                R.id.history -> {
                    startActivity(Intent(this, HistoryActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    true
                }
                R.id.settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    true
                }
                else -> false
            }
        }

        setupView()

        binding.cameraActivity.setOnClickListener() {
            val i = Intent(this@NewsActivity, CameraActivity::class.java)
            startActivity(i)
        }
        initRecyclerView()

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        newsViewModel.fetchHealthNews()
        newsViewModel.newsList.observe(this, Observer { newsList ->
            newsAdapter.submitList(newsList)
        })
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

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvNewsList.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@NewsActivity)
        }
    }

    fun openNewsUrl(view: View) {
        val url = view.getTag(R.id.tvReadMore) as? String
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishAffinity()
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}