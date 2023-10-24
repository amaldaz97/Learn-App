package com.amaldz.learnapp.ui.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.amaldz.learnapp.R
import com.amaldz.learnapp.databinding.ActivityMainBinding
import com.amaldz.learnapp.helpers.PreferenceConfig
import com.amaldz.learnapp.ui.fragments.LanguageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController
    val preferenceHelper: PreferenceConfig by lazy { PreferenceConfig(this) }
    var fontSize = MutableLiveData(null)

    val kannada = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.KANNADA)
        .build()

    val hindi = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.HINDI)
        .build()

    val kannadaTransalator = Translation.getClient(kannada)
    val hindiTransalator = Translation.getClient(hindi)

    var conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        navController = this.findNavController(R.id.nav_host_fragment_activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setupWithNavController(navController)

        kannadaTransalator.downloadModelIfNeeded(conditions).addOnSuccessListener {}.addOnFailureListener { exception -> }
        hindiTransalator.downloadModelIfNeeded(conditions).addOnSuccessListener {}.addOnFailureListener { exception -> }

    }

    fun goToSettings(view: View) {
        navController.navigate(R.id.nav_settings)
    }
    fun languageClick(view: View) {
        val languageFragment = LanguageFragment(this)
        languageFragment.show(this.supportFragmentManager, languageFragment.tag)
    }


    fun backPress(view: View) {
        onBackPressed()
    }

    override fun attachBaseContext(newBase: Context?) {
        fontSize.observe(this) {
            try {
                if (it != null) {
                    val newOverride = Configuration(newBase?.resources?.configuration)
                    newOverride.fontScale = (it + .5).toFloat()
                    applyOverrideConfiguration(newOverride)
                    preferenceHelper.setFontSize((it + .5).toFloat())
                }
            }catch (_: Exception){}
        }
        super.attachBaseContext(newBase)
    }

    fun restartApp() {
        val intent = Intent(applicationContext, SplashScreenActivity::class.java)
        startActivity(intent)
        finish()
    }
}