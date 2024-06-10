package com.app.ecosort.view.description

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.ecosort.R
import com.app.ecosort.adapter.TrashAdapter
import com.app.ecosort.databinding.ActivityDescriptionBinding
import com.app.ecosort.helper.PrefHelper

class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemsOrganic = listOf(
            TrashItem("https://2.bp.blogspot.com/-itfr5sJ62YY/WjrequhQ3sI/AAAAAAAAAMI/eMeRjglfDFsaeJcreOG7wvFeVha7OAJAQCLcBGAs/w1200-h630-p-k-no-nu/53c74051-a378-4b33-854c-2206ca2ba9b3.jpg",getString(R.string.ampasteh)),
            TrashItem("https://asset-a.grid.id/crop/0x0:0x0/700x465/photo/grid/original/130490_kulit-pisang.jpg", getString(R.string.kulitbuah)),
            TrashItem("https://c.pxhere.com/photos/d2/f2/dry_leaf_dry_leaf_tree-1238959.jpg!d",getString(R.string.daunkering)),
            TrashItem("https://c.pxhere.com/images/f5/69/965ea34b0c9055bd3d321351fe5f-1585789.jpg!d",getString(R.string.rantingpohon)),
            TrashItem("https://tse1.mm.bing.net/th?id=OIP._2Tw15rO6mcEufnU7xPBygHaE7&pid=Api&P=0&h=220",getString(R.string.bungalayu)),
            TrashItem("https://cdn-2.tstatic.net/jabar/foto/bank/images/cangkang-telur_20170616_221540.jpg",getString(R.string.cangkangtelur)),
            TrashItem("https://klikhijau.com/wp-content/uploads/2021/05/Tulang-ikan.jpg",getString(R.string.tulanghewan)),
            TrashItem("https://alihamdan.id/wp-content/uploads/2017/04/jitunews-com.jpg",getString(R.string.rumputliar)),
            TrashItem("https://3.bp.blogspot.com/-Uo5Ys_9QaSs/Vo6TykWw5eI/AAAAAAAABF4/R8yTaY1jMHU/s1600/tisu1.jpg",getString(R.string.tisu)),
            TrashItem("https://tse1.mm.bing.net/th?id=OIP.C6Qp7AST8LpHWG_vReEkQAHaE7&pid=Api&P=0&h=220",getString(R.string.ampaskopi))
        )

        val recyclerViewOrganic: RecyclerView = findViewById(R.id.rvOrganic)
        recyclerViewOrganic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewOrganic.adapter = TrashAdapter(itemsOrganic)

        val itemsAnOrganic = listOf(
            TrashItem("https://cf.shopee.co.id/file/e43becc86bd5465dd09a3027211e94d1",getString(R.string.botolplatik)),
            TrashItem("https://image.indonetwork.co.id/thumbs/300x300/categories/1511497394.jpg",getString(R.string.kantongplastik)),
            TrashItem("https://3.bp.blogspot.com/-4KYW37JKZ2g/U0DiUtMBAsI/AAAAAAAAMtA/L7nku60rwoU/s1600/Kaleng+bekas.jpg",getString(R.string.kaleng)),
            TrashItem("https://cdn.idntimes.com/content-images/post/20160715/mitos8-720e29262e101bd6b9eda997ad04e917.jpg",getString(R.string.pecahankaca)),
            TrashItem("https://4.bp.blogspot.com/-k4mj57VhtrU/VxbxRUriiDI/AAAAAAAAExY/_M_o5zo8Ac4e5aG9MxOIIWTlWsOMXZUlACLcB/s1600/jenis-baterai.jpg",getString(R.string.baterai)),
            TrashItem("https://tse3.mm.bing.net/th?id=OIP.08tAUdx0nggyo1g9wBQeHAHaFf&pid=Api&P=0&h=220",getString(R.string.ban)),
            TrashItem("https://tse3.mm.bing.net/th?id=OIP.XFWJ-PRBNQsSFGlMo_DlrAHaH5&pid=Api&P=0&h=220",getString(R.string.pakaian)),
            TrashItem("https://tse4.mm.bing.net/th?id=OIP.jPIQCM3YSt4XvmizGszK-AHaEO&pid=Api&P=0&h=220",getString(R.string.perabotan)),
            TrashItem("https://news.ralali.com/wp-content/uploads/2019/01/jenis-jenis-kabel-listrik.jpg",getString(R.string.kabel)),
            TrashItem("https://3.bp.blogspot.com/-NLAtaqHLANo/XJqwUOuGvRI/AAAAAAAABIU/gJPVqsv0o3oCg52iBKLzSfJnGJeWAjPuQCLcBGAs/s1600/lampu%2BCFL.jpg",getString(R.string.lampu))
        )

        val recyclerViewAnorganic: RecyclerView = findViewById(R.id.rvAnorganic)
        recyclerViewAnorganic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewAnorganic.adapter = TrashAdapter(itemsAnOrganic)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = binding.toolbar
        val typeface: Typeface? = ResourcesCompat.getFont(this, R.font.montserrat_semibold)
        if (typeface != null) {
            val spannableTitle = SpannableString(toolbar.title)
            val colorWhite = ContextCompat.getColor(this, android.R.color.white)
            val colorStateList = ColorStateList.valueOf(colorWhite)
            val colorFilter = PorterDuffColorFilter(colorWhite, PorterDuff.Mode.SRC_IN)
            binding.toolbar.setNavigationContentDescription("Back")
            val defaultBackIcon = binding.toolbar.navigationIcon
            defaultBackIcon?.setColorFilter(colorFilter)
            binding.toolbar.navigationIcon = defaultBackIcon
            spannableTitle.setSpan(
                TypefaceSpan(typeface),
                0,
                spannableTitle.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            toolbar.title = spannableTitle
            toolbar.setTitleTextColor(Color.WHITE)
        }

        binding.toolbar.setTitleTextColor(Color.WHITE)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setupView()
        updateTheme()
    }

    private fun updateTheme() {
        val pref = PrefHelper(this)
        val isDarkModeEnabled = pref.getBoolean("dark_mode")
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
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
    }
}