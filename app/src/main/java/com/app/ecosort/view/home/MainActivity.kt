package com.app.ecosort.view.home

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.app.ecosort.R
import com.app.ecosort.ViewModelFactory
import com.app.ecosort.databinding.ActivityMainBinding
import com.app.ecosort.helper.PrefHelper
import com.app.ecosort.view.description.DescriptionActivity
import com.app.ecosort.view.gallery.GalleryActivity
import com.app.ecosort.view.history.HistoryActivity
import com.app.ecosort.view.news.NewsActivity
import com.app.ecosort.view.settings.SettingsActivity
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@Suppress("DEPRECATION")
class MainActivity() : AppCompatActivity() {

    private val  pref by lazy { PrefHelper(this) }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageCarousel: ImageCarousel
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        updateTheme()
        setContentView(binding.root)

        overridePendingTransition(0, 0)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageCarousel = ArrayList<CarouselItem>()
        imageCarousel.add(CarouselItem("https://images.pexels.com/photos/2547565/pexels-photo-2547565.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",getString(R.string.slide_1)))
        imageCarousel.add(CarouselItem("https://d12man5gwydfvl.cloudfront.net/wp-content/uploads/2018/10/Sampah-Organik-Adalah-1.jpg",getString(R.string.slide_2)))
        imageCarousel.add(CarouselItem("https://images.pexels.com/photos/10143821/pexels-photo-10143821.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",getString(R.string.slide_3)))
        imageCarousel.add(CarouselItem("https://4.bp.blogspot.com/-yY6ZluC2BHg/VQD8ZIhnTbI/AAAAAAAAAGE/kjF9bH8XFmg/s1600/sampah-organik%2Bdaun.jpg",getString(R.string.slide_4)))
        imageCarousel.add(CarouselItem("https://images.pexels.com/photos/128421/pexels-photo-128421.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",getString(R.string.slide_5)))

        binding.carousel.setData(imageCarousel)

        mainViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[MainViewModel::class.java]

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
        setupView()
        navigateToCamera()
        navigateToDescription()

        binding.bottomNavView.selectedItemId = R.id.home

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

        binding.cameraActivity.setOnClickListener() {
            val i = Intent(this@MainActivity, GalleryActivity::class.java)
            startActivity(i)
        }
    }

    private fun navigateToDescription() {
        binding.btnNextToDescription.setOnClickListener() {
            val i = Intent(this@MainActivity, DescriptionActivity::class.java)
            startActivity(i)
        }
    }

    private fun navigateToCamera() {
        binding.btnNextToCamera.setOnClickListener() {
            val i = Intent(this@MainActivity, GalleryActivity::class.java)
            startActivity(i)
        }
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishAffinity()
        } else {
            Toast.makeText(this, getString(R.string.back), Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

}