package com.app.ecosort.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.app.ecosort.R
import com.app.ecosort.ViewModelFactory
import com.app.ecosort.api.ApiConfig
import com.app.ecosort.data.pref.UserModel
import com.app.ecosort.databinding.ActivityLoginBinding
import com.app.ecosort.helper.PrefHelper
import com.app.ecosort.view.home.MainActivity
import com.app.ecosort.view.register.RegisterActivity
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private val  pref by lazy { PrefHelper(this) }
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.show()
        loginViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[LoginViewModel::class.java]

        setupView()
        setupAction()
        playAnimation()
        setupAction2()

    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener { performLogin() }
    }
    private fun setupAction2() {
        binding.hrefSignUp.setOnClickListener {startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun performLogin() {
        binding.loading.visibility = View.VISIBLE

        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        lifecycleScope.launch {
            try {
                loginViewModel.login(email, password)
                val userModelFlow = loginViewModel.getSession()

                userModelFlow.collect { userModel ->
                    loginViewModel.saveSession(userModel)
                    ApiConfig.setAuthToken(userModel.token)
                    showDataStoredInDataStore(userModel)
                }

            } catch (e: Exception) {
                showErrorMessage(e.message ?: getString(R.string.fail_login))
            } finally {
                binding.loading.visibility = View.GONE
            }
        }
    }

    private fun showErrorMessage(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.fail_login))
            setMessage(getString(R.string.error_message))
            setPositiveButton(getString(R.string.ok), null)
            create()
            show()
        }
    }

    private fun showDataStoredInDataStore(userModel: UserModel) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.done_message))
            .setMessage(getString(R.string.done_login))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                lifecycleScope.launch {
                    loginViewModel.saveSession(userModel)
                }
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            .show()
    }

    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
        val signUp = ObjectAnimator.ofFloat(binding.hrefSignUp, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login,
                signUp
            )
            startDelay = 100
        }.start()
    }
    @Deprecated("Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishAffinity()
        } else {
            Toast.makeText(this,  getString(R.string.back), Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}