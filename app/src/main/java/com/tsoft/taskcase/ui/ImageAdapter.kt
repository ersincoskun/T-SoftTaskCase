package com.tsoft.taskcase.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tsoft.taskcase.databinding.ItemImageListBinding
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.utils.loadGlideImage

class ImageAdapter : PagingDataAdapter<ImageHit, ImageViewHolder>(IMAGE_COMPARATOR) {
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.create(parent)
    }

    companion object {
        val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<ImageHit>() {
            override fun areItemsTheSame(oldItem: ImageHit, newItem: ImageHit): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: ImageHit, newItem: ImageHit): Boolean = oldItem == newItem
        }
    }
}

class ImageViewHolder(private val binding: ItemImageListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(image: ImageHit) {
        binding.imageView.loadGlideImage(image.largeImageURL)
    }

    companion object {
        fun create(parent: ViewGroup): ImageViewHolder {
            val binding = ItemImageListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ImageViewHolder(binding)
        }
    }
}

