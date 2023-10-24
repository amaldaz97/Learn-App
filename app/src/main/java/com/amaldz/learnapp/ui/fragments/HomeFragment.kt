package com.amaldz.learnapp.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.amaldz.learnapp.R
import com.amaldz.learnapp.databinding.FragmentHomeBinding
import com.amaldz.learnapp.helpers.PreferenceConfig
import com.amaldz.learnapp.ui.activities.MainActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        setToolBar()

        return binding.root
    }

    private fun setToolBar() {
        binding.toolbar.leftTitle.text = requireContext().getText(R.string.home)
    }
}