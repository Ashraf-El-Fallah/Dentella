package com.af.dentalla.ui.setting.savedarticles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemArticleBinding
import com.af.dentalla.domain.entity.ArticleSavedEntity
import com.af.dentalla.utils.gone
import com.af.dentalla.utils.loadImage
import java.text.SimpleDateFormat
import java.util.Locale

class SavedArticleAdapter() :
    ListAdapter<ArticleSavedEntity, SavedArticleAdapter.PatientArticlesViewHolder>(
        ArticleDiffCallBack()
    ) {

    inner class PatientArticlesViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleSavedEntity) {
            binding.apply {
                baseItem.apply {
                    textViewDoctorNameArticle.text = article.doctorName
                    textViewTime.text = formatDateTime(article.postingTime)
                    textViewArticleTitle.text = article.title
                    textViewArticleContent.text = article.content
                    imageTeeth.loadImage(article.articleImage)
                    imgDoctorArticle.loadImage(article.doctorImage)
                    btnAddToFavorites.gone()
                }
            }
        }
    }

    private fun formatDateTime(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.getDefault())

        val date = inputFormat.parse(input)
        return outputFormat.format(date)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientArticlesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return PatientArticlesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientArticlesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private class ArticleDiffCallBack : DiffUtil.ItemCallback<ArticleSavedEntity>() {
        override fun areItemsTheSame(
            oldItem: ArticleSavedEntity,
            newItem: ArticleSavedEntity
        ): Boolean {
            return oldItem.articleId == newItem.articleId
        }

        override fun areContentsTheSame(
            oldItem: ArticleSavedEntity,
            newItem: ArticleSavedEntity
        ): Boolean {
            return oldItem == newItem
        }

    }
}