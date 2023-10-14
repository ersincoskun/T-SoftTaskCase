package com.tsoft.taskcase.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tsoft.taskcase.R
import com.tsoft.taskcase.databinding.ItemImageListBinding
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.utils.loadGlideImage
import com.tsoft.taskcase.utils.onSingleClickListener
import com.tsoft.taskcase.utils.setClickEffect

class ImageAdapter(val itemClickAction: (ImageHit) -> Unit, val addFavoriteAction: (ImageHit) -> Unit, val deleteFavoriteAction: (Int) -> Unit) : PagingDataAdapter<ImageHit, ImageAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ImageViewHolder(binding)
    }

    companion object {
        val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<ImageHit>() {
            override fun areItemsTheSame(oldItem: ImageHit, newItem: ImageHit): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: ImageHit, newItem: ImageHit): Boolean = oldItem == newItem
        }
    }

    inner class ImageViewHolder(private val binding: ItemImageListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageHit: ImageHit) {
            binding.apply {
                civUserProfilePicture.loadGlideImage(imageHit.userImageURL)
                tvUsername.text = imageHit.user
                imageView.loadGlideImage(imageHit.webformatURL)
                ivAddFavoriteIcon.setImageResource(
                    if (imageHit.isFavorite) R.drawable.added_favorite_icon else R.drawable.not_added_favorite_icon
                )
                ivAddFavoriteIcon.onSingleClickListener {
                    if (imageHit.isFavorite) {
                        deleteFavoriteAction.invoke(imageHit.id)
                    } else {
                        addFavoriteAction.invoke(imageHit)
                    }
                }
                cardView.onSingleClickListener {
                    itemClickAction.invoke(imageHit)
                }
                cardView.setClickEffect()
                ivAddFavoriteIcon.setClickEffect()
            }
        }
    }

}

