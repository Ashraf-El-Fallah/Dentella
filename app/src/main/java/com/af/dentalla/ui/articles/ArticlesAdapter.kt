package com.af.dentalla.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.BaseItemBinding
import com.af.dentalla.databinding.ItemArticleBinding
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.utilities.loadImage

class ArticlesAdapter :
    ListAdapter<ArticlesEntity, ArticlesAdapter.PatientArticlesViewHolder>(
        ArticleDiffCallBack()
    ) {

    inner class PatientArticlesViewHolder(
        private val binding: ItemArticleBinding,
        private val baseItemBinding: BaseItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticlesEntity) {
            binding.apply {
                imageTeeth.loadImage(article.articleImage.toString())
                textViewArticleTitle.text = article.title
                textViewArticleContent.text = article.content
            }
            baseItemBinding.apply {
                textViewDoctorNameArticle.text = article.doctorName
                textViewTime.text = article.postingTime
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientArticlesViewHolder {
        return PatientArticlesViewHolder(
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            BaseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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