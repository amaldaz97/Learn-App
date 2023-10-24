package com.amaldz.learnapp.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.amaldz.learnapp.R
import com.amaldz.learnapp.databinding.FragmentLearnBinding
import com.amaldz.learnapp.helpers.PreferenceConfig
import com.amaldz.learnapp.ui.activities.MainActivity
import com.amaldz.learnapp.ui.adapters.TabPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LearnFragment : Fragment() {

    private lateinit var binding: FragmentLearnBinding
    private lateinit var adapter: TabPagerAdapter
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_learn,container,false)
        setToolBar()
        setTabAdapter()

        return binding.root
    }

    private fun setTabAdapter() {
        binding.vpLearn.isSaveEnabled = false
        val numberOfTabs = 6

        binding.learnTl.tabMode = TabLayout.MODE_AUTO
        binding.learnTl.isInlineLabel = true
        
        adapter = TabPagerAdapter(parentFragmentManager, lifecycle, numberOfTabs)
        binding.vpLearn.adapter = adapter
        binding.vpLearn.isUserInputEnabled = true
        binding.vpLearn.offscreenPageLimit = 6

        TabLayoutMediator(
            binding.learnTl,
            binding.vpLearn
        ) { tab, position ->
            when (position) {
                0 -> tab.text = requireContext().getText(R.string.all)
                1 -> tab.text = requireContext().getText(R.string.news)
                2 -> tab.text = requireContext().getText(R.string.best_practises)
                3 -> tab.text = requireContext().getText(R.string.advisory)
                4 -> tab.text = requireContext().getText(R.string.alerts)
                5 -> tab.text = requireContext().getText(R.string.mobile)
            }
        }.attach()
    }

    private fun setToolBar() {
        binding.toolbar.leftTitle.text = requireContext().getText(R.string.learn)
    }
}