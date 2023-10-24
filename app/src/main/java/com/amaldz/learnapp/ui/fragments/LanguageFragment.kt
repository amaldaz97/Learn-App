package com.amaldz.learnapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.amaldz.learnapp.R
import com.amaldz.learnapp.databinding.FragmentLanguageBinding
import com.amaldz.learnapp.helpers.PreferenceConfig
import com.amaldz.learnapp.ui.activities.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.launch

class LanguageFragment(val mainActivity: MainActivity) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentLanguageBinding
    val preferenceHelper: PreferenceConfig by lazy { PreferenceConfig(requireContext()) }

    // Create an English-Custom Lang translator:

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_language, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SetDefaultLanguage()

        kannadaTransalator.downloadModelIfNeeded(conditions).addOnSuccessListener {}.addOnFailureListener { exception -> }
        hindiTransalator.downloadModelIfNeeded(conditions).addOnSuccessListener {}.addOnFailureListener { exception -> }

        try {
            binding.radioGroup.setOnCheckedChangeListener { _, _ ->
                if (binding.englishRb.isChecked) {
                    lifecycleScope.launch {
                        Toast.makeText(
                            requireContext(),
                            "App Language changed to English",
                            Toast.LENGTH_SHORT
                        ).show()
                        preferenceHelper.setLanguage("en")
                        dismiss()
                        mainActivity.restartApp()
                    }
                } else if (binding.kannadaRb.isChecked) {
                    kannadaTransalator.downloadModelIfNeeded(conditions)
                        .addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                "App Language changed to Kannada",
                                Toast.LENGTH_SHORT
                            ).show()

                            preferenceHelper.setLanguage("kn")
                            dismiss()
                            mainActivity.restartApp()

                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(requireContext(), exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                } else if (binding.hindiRb.isChecked) {
                    hindiTransalator.downloadModelIfNeeded(conditions)
                        .addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                "App Language changed to Hindi",
                                Toast.LENGTH_SHORT
                            ).show()
                            preferenceHelper.setLanguage("hi")
                            dismiss()
                            mainActivity.restartApp()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(requireContext(), exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            }
        }catch (e: Exception){}

    }

    private fun SetDefaultLanguage() {
        binding.englishRb.isChecked = false
        binding.kannadaRb.isChecked = false
        binding.hindiRb.isChecked = false

        if (preferenceHelper.getLanguage() == "en"){
            binding.englishRb.isChecked = true
        }else if (preferenceHelper.getLanguage() == "hi"){
            binding.hindiRb.isChecked = true
        }else if (preferenceHelper.getLanguage() == "kn"){
            binding.kannadaRb.isChecked = true
        }
    }
}