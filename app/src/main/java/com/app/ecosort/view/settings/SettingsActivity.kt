package com.app.ecosort.view.settings

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.ecosort.R
import com.app.ecosort.databinding.ActivitySettingsBinding
import com.app.ecosort.helper.PrefHelper
import com.app.ecosort.view.camera.CameraActivity
import com.app.ecosort.view.home.MainActivity
import com.app.ecosort.view.info.InfoActivity
import com.app.ecosort.view.news.NewsActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val  pref by lazy { PrefHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        switchTheme.isChecked = pref.getBoolean("dark_mode")

        switchTheme.setOnCheckedChangeListener { compoundButton, isChecked ->
            when (isChecked) {
                true -> {
                    pref.put("dark_mode", true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                false -> {
                    pref.put("dark_mode", false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

        }

        binding.bottomNavView.selectedItemId = R.id.settings

        binding.bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.news -> {
                    startActivity(Intent(this, NewsActivity::class.java))
                    true
                }
                R.id.info -> {
                    startActivity(Intent(this, InfoActivity::class.java))
                    true
                }
                R.id.settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }

        binding.cameraActivity.setOnClickListener() {
            val i = Intent(this@SettingsActivity, CameraActivity::class.java)
            startActivity(i)
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        showExitConfirmationDialog()
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirmation of Exit")
            .setMessage("Are you sure you want to exit the app?")
            .setPositiveButton("Yes") { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}