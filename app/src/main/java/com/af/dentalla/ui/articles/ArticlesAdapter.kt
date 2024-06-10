package com.af.dentalla.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemArticleBinding
import com.af.dentalla.domain.entity.ArticlesEntity
import com.af.dentalla.utils.loadImage
import java.text.SimpleDateFormat
import java.util.Locale

class ArticlesAdapter :
    ListAdapter<ArticlesEntity, ArticlesAdapter.PatientArticlesViewHolder>(
        ArticleDiffCallBack()
    ) {

    inner class PatientArticlesViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticlesEntity) {
            binding.apply {
                baseItem.imgDoctorArticle.loadImage(article.articleImage.toString())
//                textViewArticleTitle.text = article.title
                textViewArticleContent.text = article.content
                baseItem.textViewDoctorNameArticle.text = article.doctorName

                val formattedPostingTime = formatDateTime(article.postingTime)
                baseItem.textViewTime.text = formattedPostingTime
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

    private class ArticleDiffCallBack : DiffUtil.ItemCallback<ArticlesEntity>() {
        override fun areItemsTheSame(oldItem: ArticlesEntity, newItem: ArticlesEntity): Boolean {
            return oldItem.articleId == newItem.articleId
        }

        override fun areContentsTheSame(oldItem: ArticlesEntity, newItem: ArticlesEntity): Boolean {
            return oldItem == newItem
        }

    }
}