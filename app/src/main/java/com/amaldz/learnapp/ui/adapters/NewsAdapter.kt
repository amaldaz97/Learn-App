package com.amaldz.learnapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amaldz.learnapp.R
import com.amaldz.learnapp.databinding.NewsItemBinding
import com.amaldz.learnapp.helpers.PreferenceConfig
import com.amaldz.learnapp.ui.activities.MainActivity
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONObject

class NewsAdapter(
    val context: Context,
    var newsCount: Int,
    var newsDatas: JSONArray,
    val mainActivity: MainActivity,
) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater
    val preferenceHelper: PreferenceConfig by lazy { PreferenceConfig(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        val binding: NewsItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.news_item,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val newsData: JSONObject = newsDatas.getJSONObject(position)

            if (preferenceHelper.getLanguage() == "en") {
                holder.binding.title.text = newsData.getString("title")
                holder.binding.description.text = newsData.getString("description")
            }else if (preferenceHelper.getLanguage() == "hi"){
                mainActivity.hindiTransalator.translate(newsData.getString("title"))
                    .addOnSuccessListener { translatedText ->
                        holder.binding.title.text = translatedText
                    }
                    .addOnFailureListener {
                        holder.binding.title.text = newsData.getString("title")
                    }
                mainActivity.hindiTransalator.translate(newsData.getString("description"))
                    .addOnSuccessListener { translatedText ->
                        holder.binding.description.text = translatedText

                    }
                    .addOnFailureListener {
                        holder.binding.description.text = newsData.getString("description")
                    }
            }else if (preferenceHelper.getLanguage() == "kn"){
                mainActivity.kannadaTransalator.translate(newsData.getString("title"))
                    .addOnSuccessListener { translatedText ->
                        holder.binding.title.text = translatedText
                    }
                    .addOnFailureListener {
                        holder.binding.title.text = newsData.getString("title")
                    }

                mainActivity.kannadaTransalator.translate(newsData.getString("description"))
                    .addOnSuccessListener { translatedText ->
                        holder.binding.description.text = translatedText
                    }
                    .addOnFailureListener {
                        holder.binding.description.text = newsData.getString("description")
                    }
            }

            holder.binding.pubTime.text = newsData.getString("pubDate")
            Glide.with(context)
                .load(newsData.getString("image_url"))
                .placeholder(R.drawable.news_placeholder)
                .into(holder.binding.newsImage)

        }catch (_: Exception){}
    }

    override fun getItemCount(): Int {
        return newsCount
    }

    inner class ViewHolder(var binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}