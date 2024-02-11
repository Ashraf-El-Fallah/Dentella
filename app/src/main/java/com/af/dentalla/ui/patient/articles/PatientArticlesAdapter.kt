package com.af.dentalla.ui.patient.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemArticleBinding
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.utilities.loadImage

class PatientArticlesAdapter :
    ListAdapter<ArticlesEntity, PatientArticlesAdapter.PatientArticlesViewHolder>(ArticleDiffCallBack()) {

    inner class PatientArticlesViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticlesEntity) {
            binding.apply {
                imageTeeth.loadImage(article.articleImage)
                textViewDoctorNameArticle.text = article.doctorName
                textViewTime.text = article.postingTime
                textViewArticleTitle.text = article.title
                textViewArticleContent.text = article.content
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientArticlesViewHolder {
        return PatientArticlesViewHolder(
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PatientArticlesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private class ArticleDiffCallBack : DiffUtil.ItemCallback<ArticlesEntity>() {
        override fun areItemsTheSame(oldItem: ArticlesEntity, newItem: ArticlesEntity): Boolean {
            return oldItem.articleId == newItem.articleId
        }

        override fun areContentsTheSame(oldItem: ArticlesEntity, newItem: ArticlesEntity): Boolean {
            return oldItem == newItem
        }

    }
}