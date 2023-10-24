package com.amaldz.learnapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import com.amaldz.learnapp.R
import com.amaldz.learnapp.databinding.FragmentSettingsBinding
import com.amaldz.learnapp.helpers.PreferenceConfig
import com.amaldz.learnapp.ui.activities.MainActivity

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    val preferenceHelper: PreferenceConfig by lazy { PreferenceConfig(requireContext()) }

    var mainActivity = MainActivity()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_settings,container,false)

        binding.textSize.progress = ((preferenceHelper.getFontSize() - .5) * 100).toInt()

        binding.textSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                preferenceHelper.setFontSize(((binding.textSize.progress.toFloat() / 100) + .5).toFloat())
                mainActivity.restartApp()
            }
        })

        if (preferenceHelper.getLanguage() == "en") {
            binding.languageTxt.text = requireContext().getString(R.string.english)
        }else if (preferenceHelper.getLanguage() == "kn"){
            binding.languageTxt.text =requireContext().getString(R.string.kannada)
        }else if (preferenceHelper.getLanguage() == "hi"){
            binding.languageTxt.text = requireContext().getString(R.string.hindi)
        }

        return binding.root
    }
}