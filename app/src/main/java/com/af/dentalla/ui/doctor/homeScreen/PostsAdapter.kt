package com.af.dentalla.ui.doctor.homeScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.BaseItemBinding
import com.af.dentalla.databinding.BaseTextBinding
import com.af.dentalla.databinding.ItemPostBinding
import com.af.dentalla.domain.entity.PostEntity
import com.af.dentalla.utilities.loadImage

class PostsAdapter : ListAdapter<PostEntity, PostsAdapter.PostViewHolder>(PostDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            BaseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            BaseTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class PostViewHolder(
        private val binding: ItemPostBinding,
        private val baseItemBinding: BaseItemBinding,
        private val baseTextBinding: BaseTextBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostEntity) {
            binding.apply {

            }
            baseItemBinding.apply {
                imgDoctorArticle.loadImage(post.patientPhoto.toString())
                textViewDoctorNameArticle.text = post.patientName
            }
            baseTextBinding.textViewArticleContent.text = post.content
        }
    }

    private class PostDiffCallBack : DiffUtil.ItemCallback<PostEntity>() {
        override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
            return oldItem == newItem
        }

    }
}