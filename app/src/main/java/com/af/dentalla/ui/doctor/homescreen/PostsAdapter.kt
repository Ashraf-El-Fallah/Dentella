package com.af.dentalla.ui.doctor.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.af.dentalla.databinding.ItemPostBinding
import com.af.dentalla.domain.entity.PostEntity
import com.af.dentalla.utils.loadImage

class PostsAdapter : ListAdapter<PostEntity, PostsAdapter.PostViewHolder>(PostDiffCallBack()) {

    inner class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostEntity) {
            binding.apply {
                baseItem.apply {
                    textViewDoctorNameArticle.text = post.patientName
                    imgDoctorArticle.loadImage(post.patientPhoto.toString())
                    textViewTime.text = post.phoneNumber
                }
                baseText.textViewArticleContent.text = post.content
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(currentList[position])
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