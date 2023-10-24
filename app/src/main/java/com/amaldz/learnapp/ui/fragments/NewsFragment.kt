package com.amaldz.learnapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaldz.learnapp.R
import com.amaldz.learnapp.databinding.FragmentNewsBinding
import com.amaldz.learnapp.helpers.PreferenceConfig
import com.amaldz.learnapp.ui.activities.MainActivity
import com.amaldz.learnapp.ui.adapters.NewsAdapter
import org.json.JSONArray
import org.json.JSONObject


class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter

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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false)

        var newsData: JSONObject = getDummyNews()
        buildNews(newsData.getJSONArray("results"),newsData.getInt("totalResults"))
        return binding.root
    }

    private fun getDummyNews(): JSONObject {
        val fileInString: String =
            requireContext().assets.open("newsData.json").bufferedReader().use { it.readText() }
        return JSONObject(fileInString)

    }

    private fun buildNews(newsData: JSONArray, newsCount: Int) {
        newsAdapter = NewsAdapter(requireContext(),newsCount,newsData,mainActivity)
        binding.rvNews.layoutManager = LinearLayoutManager(context)
        binding.rvNews.adapter = newsAdapter
    }

}