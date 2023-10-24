package com.amaldz.learnapp.ui.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amaldz.learnapp.databinding.ActivitySplashScreenBinding
import com.amaldz.learnapp.helpers.PreferenceConfig
import java.util.Locale

class SplashScreenActivity : AppCompatActivity() {

    private var binding: ActivitySplashScreenBinding? = null
    private var TIME_OUT: Long = 3000
    val preferenceHelper: PreferenceConfig by lazy { PreferenceConfig(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(binding!!.root)

        setLanguage(preferenceHelper.getLanguage())

        launchActivity()
    }

    private fun launchActivity() {
        try {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            },TIME_OUT)
        }catch (e: Exception){
            Log.i("ERROR",e.toString())
        }
    }
    override fun attachBaseContext(newBase: Context?) {
        val preferenceHelper: PreferenceConfig by lazy { PreferenceConfig(newBase!!) }
        try {
            newBase?.resources?.configuration?.fontScale = preferenceHelper.getFontSize()
        }catch (e: Exception){
            newBase?.resources?.configuration?.fontScale = 1.0f
            Toast.makeText(newBase,e.toString(),Toast.LENGTH_SHORT).show()
        }
        super.attachBaseContext(newBase)
    }

    private fun setLanguage(language: String){
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )

    }
}

